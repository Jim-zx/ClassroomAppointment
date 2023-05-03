
const routes = [
  // {
  //   path: '/',
  //   component: () => import('layouts/MainLayout.vue'),
  //   children: [
  //     { path: '', component: () => import('pages/IndexPage.vue') },
  //     { path: '/test', component: () => import('pages/p2.vue') }
  //   ]
  // },
  {
    path: '/send',
    meta: { auth: false },
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', name: 'page', component: () => import('pages/p2.vue') }
    ]
  },
  {
    path: '/login',
    meta: { auth: true },
    component: () => import('layouts/BeforeLogin.vue'),
    children: [
      // login
      { path: '', name: 'login', component: () => import('pages/Auth/login.vue') },
      // reset password
      { path: 'reset', component: () => import('pages/Auth/reset.vue') }
    ]
  },
  {
    path: '/main',
    name: 'main',
    meta: { auth: false },
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', name: 'homepage', component: () => import('pages/IndexPage.vue') }
    ]
  },
  {
    path: '/module',
    name: 'module',
    meta: { auth: false },
    component: () => import('layouts/ModuleLayout.vue')
  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
