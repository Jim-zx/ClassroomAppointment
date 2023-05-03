<template>
  <div class="q-pa-md">
    <q-table :title="$t('module.classroom.name')" :rows="rows" :columns="columns" row-key="id" binary-state-sort
      :selection="user.authorityId === 'host'?'multiple':'none'" v-model:selected="selected" :filter="filter" virtual-scroll style="max-height: 600px"
      v-model:pagination="pagination" :rows-per-page-options="[0]" :no-data-label="$t('table.nodata')"
      :no-results-label="$t('table.noresult')" separator="cell">
      <template v-slot:bottom>
        <q-btn-group push v-if="this.user.authorityId==='host'">
        <q-btn color="primary" :label="$t('module.classroom.feature.add')" @click="addbtn = true" />
        <q-btn color="grey-9" :label="$t('module.classroom.feature.delete')" @click="deleteClassroom()" />
        </q-btn-group>
      </template>
      <template v-slot:top-right>
        <q-input borderless dense debounce="300" v-model="filter" placeholder="Search">
          <template v-slot:append>
            <q-icon name="search" />
          </template>
        </q-input>
      </template>
    </q-table>
  </div>
<!-- add -->
  <q-dialog v-model="addbtn" persistent v-if="user.authorityId === 'host'">
    <q-card style="min-width: 350px">
      <q-card-section>
        <div class="text-h6">{{ this.$t("module.classroom.feature.add") }}</div>
      </q-card-section>

      <q-card-section class="q-gutter-y-md">
        <q-select outlined v-model="data.tower" :options="select.tower" :label="$t('entity.classroom.tower')" />
        <q-select outlined v-model="data.num" :options="select.num" :label="$t('entity.classroom.num')" />
        <q-select outlined  v-model="data.type" :options="select.type" :label="$t('entity.classroom.type')" />
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat :label="$t('button.save')" @click="addClassroom"/>
        <q-btn flat :label="$t('button.cancel')" v-close-popup/>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { userStore } from 'src/stores/user-store'
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'classroomPage',
  data () {
    return {
      addbtn: false,
      select: {
        tower: ['6A', '6B', '6C', 'E1A', 'E1B', 'E1C', 'E2A', 'E2B', 'E2C'],
        num: ['101', '102', '103', '104', '105', '106', '107', '108', '109', '110',
          '201', '202', '203', '204', '205', '206', '207', '208', '209', '210',
          '301', '302', '303', '304', '305', '306', '307', '308', '309', '310'],
        type: ['classroom', 'laboratory']
      },
      data: {
        tower: '',
        num: '',
        type: 'classroom'
      },
      filter: '',
      pagination: {
        rowsPerPage: 0
      },
      rows: [],
      user: userStore().user,
      columns: [
        {
          name: 'position',
          required: true,
          label: this.$t('entity.classroom.position'),
          align: 'left',
          field: (row) => row.tower + row.num,
          sortable: true
        },
        {
          name: 'id',
          align: 'left',
          label: this.$t('entity.classroom.id'),
          field: (row) => row.id,
          sortable: true
        },
        {
          name: 'type',
          align: 'left',
          label: this.$t('entity.classroom.type'),
          field: (row) => row.type
        }
      ],
      selected: []
    }
  },
  created () {
    this.load()
    userStore().load()
  },
  methods: {
    load () {
      this.$api.get('/getAllClassroom').then(res => {
        if (res.data.code === 200) {
          this.rows = res.data.datas
        }
      })
    },
    deleteClassroom () {
      this.$api.post('/deleteClassroom', this.selected).then(res => {
        // console.log(res)
        if (res.data.code === 200) {
          this.load()
        }
      })
    },
    addClassroom () {
      const data = [this.data]
      this.$api.post('/addClassroom', data).then(res => {
        if (res.data.code === 200) {
          this.load()
          this.addbtn = false
        }
      })
    }
  }
})
</script>
