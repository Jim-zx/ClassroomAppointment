import { defineStore } from 'pinia'
import { api } from 'src/boot/axios'

export const tokenStore = defineStore('token', {
  state: () => ({
    token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
  }),
  getters: {
    // doubleCount: (state) => state.counter * 2
  },
  actions: {
    storeToken (token) {
      // eslint-disable-next-line dot-notation
      api.defaults.headers.common['Authorization'] = token
      this.token = token
      localStorage.setItem('token', token)
    },
    cleanToken () {
      // eslint-disable-next-line dot-notation
      api.defaults.headers.common['Authorization'] = ''
      this.token = ''
      localStorage.removeItem('token')
    }
  }
})
