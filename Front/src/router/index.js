import { route } from 'quasar/wrappers'
import { api } from 'src/boot/axios'
import { tokenStore } from 'src/stores/example-store'
import { userStore } from 'src/stores/user-store'
import { createRouter, createMemoryHistory, createWebHistory, createWebHashHistory } from 'vue-router'
import routes from './routes'
/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

let Router = {}

export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory)

  Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    history: createHistory(process.env.VUE_ROUTER_BASE)
  })
  Router.beforeEach((to, from, next) => {
    console.log('from ' + from.path + ' to ' + to.path)
    const token = localStorage.getItem('token')
    // 动态路由 dynamic routing
    const dyRouter = userStore().moduleList
    // console.log(token)
    // 如果遇到刷新，则在进入一个路由前将header从新添加
    // If a refresh is encountered, the header is re-added before
    // entering a route
    tokenStore().storeToken(token)

    // 判断路由访问是否需要的登录
    // Determines whether a login is required for route access
    // Auth为true时，不需要登录,undefined为404无网页返回登录页
    // When Auth is true, no login is required.undefined
    // is 404 No web page is returned to the login page
    api.get('/check').then(res => {
      const verfity = res.data.datas
      let pageVer = 'homepage'
      userStore().load()
      if (verfity) {
        if (dyRouter) {
          dyRouter.forEach(module => {
            if (module.route === to.path) {
              pageVer = module.moduleName
            }
            if (!Router.hasRoute({ name: module.moduleName })) {
              Router.addRoute('module',
                {
                  path: module.route,
                  name: module.moduleName,
                  meta: { auth: false },
                  component: () =>
                    import('../pages/' + module.moduleName + '.vue')
                }
              )
            }
          })
        }
        // console.log(to.meta.auth)
        if (to.meta.auth) {
          next({ name: 'homepage' })
        } else if (to.meta.auth === undefined) {
          next({ name: pageVer })
        } else {
          next()
        }
      } else {
        if (to.meta.auth) {
          next()
        } else if (to.meta.auth === undefined) {
          next({ name: 'login' })
        } else {
          next({ name: 'login' })
        }
      }
    }).catch((err) => {
      console.log(err)
      if (to.path !== '/login') {
        next({ name: 'login' })
      } else {
        next()
      }
    })
  })

  return Router
})
export { Router }
