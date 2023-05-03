<template>
  <q-page class="flex flex-center">
    <q-card class="q-pa-md" style="height:350px">

      <q-card-section>
        <div class="text-h6 text-center">Login</div>
      </q-card-section>
      <q-card-section>

    <q-form class="q-gutter-md">
    <q-input outlined v-model="data.account" :label="$t('user.account')" />
    <q-input type="password" outlined v-model="data.password" :label="$t('user.password')" />

  </q-form>
</q-card-section>
<q-card-section class="q-pa-md row">
  <q-btn class="text-primary col-12" :label="$t('button.submit')" @click="submit" ></q-btn>
</q-card-section>
</q-card>
  </q-page>
</template>

<script>
import { defineComponent } from 'vue'
import { tokenStore } from 'src/stores/example-store'
import { userStore } from 'src/stores/user-store'
export default defineComponent({
  name: 'LoginPage',
  created () {
  },
  data () {
    return {
      data: {
        account: '12345',
        password: '1234'
      }
    }
  },
  mounted () {
    // console.log('token:' + tokenStore().token)
  },
  methods: {
    submit () {
      this.$api.post('/login', this.data).then(res => {
        if (res.data.code === 200) {
          tokenStore().storeToken(res.data.datas)
          userStore().loadModule()
          // add token to axios header
          this.$router.push('/main')
        } else {
          this.$q.dialog({
            message: this.$t('auth.fail')
          })
        }
      })
    },
    test () {
      this.$api.get('/test').then(res => {
        console.log(res)
      }).catch((err) => {
        if (err.response) {
          if (err.response.status === 500) {
            this.$q.dialog({
              message: 'No Login'
            })
          }
        }
      })
    }
  }
})
</script>
