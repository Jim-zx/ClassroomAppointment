import { defineStore } from 'pinia'
import { api } from 'boot/axios'

export const entityStore = defineStore('entity', {
  state: () => ({
    classroom: [],
    lesson: []
  }),
  getters: {
    // doubleCount: (state) => state.counter * 2
  },
  actions: {
    loadClassroom () {
      api.get('/getAllClassroom').then((res) => {
        // console.log(res)
        if (res.data.code === 200) {
          this.classroom = res.data.datas
        }
      })
    },
    loadLesson () {
      api.get('/getAllLesson').then((res) => {
        // console.log(res)
        if (res.data.code === 200) {
          this.classroom = res.data.datas
        }
      })
    }
  }
})
