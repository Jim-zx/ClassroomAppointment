<template>
  <q-parallax
      src="../assets/R-C.jpg"
    >
    </q-parallax>
  <q-page class="q-pa-md q-gutter-y-md" >
    <!-- <div class="row q-gutter-md">
      <div class="offset-2">
        {{this.$t('title')}}
      </div>
    </div> -->
    <div class="flex flex-center"
     style="border-left:solid 1px grey;
     border-right:solid 1px grey;">
      <q-card bordered class="my-card bg-grey-4" style="width: 700px;">
        <q-card-section class="row">
          <div class="text-h4 offset-md-4">{{$t('main.functionList')}}</div>
        </q-card-section>

        <q-separator class="bg-pink" inset />

        <q-card-section class="row q-gutter-y-md">
          <q-btn
          v-for="module in userInfo.moduleList"
          :key="module.id"
          class="col-12"
          color="primary"
          :icon="module.icon"
          :label="$t(module.moduleName+'.name')"
          :to="module.route"
          align="left"
          glossy
          />
        </q-card-section>
      </q-card>
    </div>
  </q-page>
</template>

<script>
import { defineComponent } from 'vue'
import { userStore } from 'src/stores/user-store'
export default defineComponent({
  name: 'HomePage',
  created () {
  },
  data () {
    return {
      userInfo: userStore()
    }
  },
  methods: {

  }
})
</script>
