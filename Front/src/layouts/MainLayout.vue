<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-toolbar-title>
          <q-avatar>
            <q-btn round icon="home" :ripple="false" />
          </q-avatar>
          {{$t("title")}}
        </q-toolbar-title>

      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>

    <q-footer elevated class="bg-grey-8 text-white">
      <q-toolbar>
        <q-toolbar-title>
          <div>{{this.userInfo.user.username}}</div>
        </q-toolbar-title>
        <q-btn dense flat round icon="logout" @click="logout" />
      </q-toolbar>
    </q-footer>

  </q-layout>
</template>

<script>
import { tokenStore } from 'src/stores/example-store'
import { userStore } from 'src/stores/user-store'
export default {
  beforeCreate () {
    userStore().load()
  },
  mounted () {

  },
  data () {
    return {
      userInfo: userStore()
    }
  },

  methods: {
    logout () {
      this.$api.get('/logout').then(res => {
        // console.log(res)
        if (res.data.code === 200) {
          tokenStore().cleanToken()
          localStorage.removeItem('module')
          this.$router.push('/login')
        } else {
          this.$q.dialog({
            message: 'Already logout'
          })
        }
      })
    }
  }
}
</script>
