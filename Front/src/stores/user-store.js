import { defineStore } from 'pinia'
import { api } from 'boot/axios'

export const userStore = defineStore('user', {
  state: () => ({
    user: '',
    moduleList: localStorage.getItem('module') ? JSON.parse(localStorage.getItem('module')) : []
  }),
  getters: {
    // doubleCount: (state) => state.counter * 2
  },
  actions: {
    load () {
      api.get('/user').then((res) => {
        // console.log(res)
        if (res.data.code === 200) {
          this.user = res.data.datas
        }
      }).catch((err) => {
        if (err.response) {
          if (err.response.status === 500) {
            // eslint-disable-next-line dot-notation
            localStorage.removeItem('token')
          }
        }
      })
    },
    loadModule () {
      return new Promise(() => {
        api.get('/getModule').then((res) => {
          // console.log(res)
          if (res.data.code === 200) {
            this.moduleList = res.data.datas
            localStorage.setItem('module', JSON.stringify(this.moduleList))
          }
        })
      })
    }
  }
})
