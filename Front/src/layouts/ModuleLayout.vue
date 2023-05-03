<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-toolbar-title>
          <q-avatar>
            <q-btn round icon="home" to="/main"/>
          </q-avatar>
          {{$t("title")}}
        </q-toolbar-title>

      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>
    </q-layout>
</template>
<script>
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
  }
}
</script>
