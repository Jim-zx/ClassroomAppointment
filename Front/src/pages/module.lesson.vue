<template>
  <div class="q-pa-md">
    <q-table :title="$t('module.lesson.name')" :rows="rows" :columns="columns" row-key="id" binary-state-sort
      :selection="user.authorityId === 'host'?'multiple':'none'" v-model:selected="selected" :filter="filter" virtual-scroll style="max-height: 600px"
      v-model:pagination="pagination" :rows-per-page-options="[0]" :no-data-label="$t('table.nodata')"
      :no-results-label="$t('table.noresult')" separator="cell">
      <template v-slot:bottom>
        <q-btn-group push v-if="this.user.authorityId==='host'">
        <q-btn color="primary" :label="$t('module.lesson.feature.add')" @click="addbtn = true" />
        <q-btn color="grey-9" :label="$t('module.lesson.feature.delete')" @click="deleteLesson()" />
        <q-btn color="yellow-9" :label="$t('module.lesson.feature.addTimetable')" @click="addTimetablebtn=true" />
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
  <!-- add lesson-->
  <q-dialog v-model="addbtn" persistent v-if="user.authorityId === 'host'">
    <q-card style="min-width: 350px">
      <q-card-section>
        <div class="text-h6">{{ this.$t("module.lesson.feature.add") }}</div>
      </q-card-section>

      <q-card-section class="q-gutter-y-md">
        <q-input outlined v-model="data.name"  :label="$t('entity.lesson.name')" />
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat :label="$t('button.save')" @click="addLesson"/>
        <q-btn flat :label="$t('button.cancel')" v-close-popup/>
      </q-card-actions>
    </q-card>
  </q-dialog>
  <!-- add timetable-->
  <q-dialog v-model="addTimetablebtn" persistent v-if="user.authorityId === 'host'">
    <q-card style="min-width: 350px">
      <q-card-section>
        <div class="text-h6">{{ this.$t("module.lesson.feature.addTimetable") }}</div>
      </q-card-section>

      <q-card-section class="q-gutter-y-md">
        <q-select :map-options="true" :emit-value="true" filled v-model="timetable.lessonId" :options="selectedLesson" :label="$t('entity.timetable.lessonId')" />
        <q-select :map-options="true" :emit-value="true" filled v-model="timetable.roomId" :options="selectedRoom" :label="$t('entity.timetable.roomId')" />
        <q-select :map-options="true" :emit-value="true" filled v-model="timetable.day" :options="selectedDay" :label="$t('entity.timetable.day')" />
        <q-select :map-options="true" :emit-value="true" :multiple="true" filled v-model="timetable.time" :options="selectedTime" :label="$t('entity.timetable.time')" />
        <q-input filled v-model="timetable.startDate" mask="####-##-##" :label="$t('entity.timetable.startDate')">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                <q-date v-model="timetable.startDate">
                  <div class="row items-center justify-end">
                    <q-btn v-close-popup label="Close" color="primary" flat />
                  </div>
                </q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>
        <q-input filled v-model="timetable.endDate" mask="####-##-##" :label="$t('entity.timetable.endDate')">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                <q-date v-model="timetable.endDate" mask="YYYY-MM-DD">
                  <div class="row items-center justify-end">
                    <q-btn v-close-popup label="Close" color="primary" flat />
                  </div>
                </q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat :label="$t('button.save')" @click="addTimetable()"/>
        <q-btn flat :label="$t('button.cancel')" v-close-popup/>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { userStore } from 'src/stores/user-store'
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'IndexPageax',
  data () {
    return {
      addbtn: false,
      addTimetablebtn: false,
      timetable: {
        lessonId: '',
        activeId: -1,
        roomId: '',
        day: '',
        time: [],
        startDate: '',
        endDate: ''
      },
      data: {
        name: ''
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
          label: this.$t('entity.lesson.name'),
          align: 'left',
          field: (row) => row.name,
          sortable: true
        },
        {
          name: 'id',
          align: 'left',
          label: this.$t('entity.lesson.id'),
          field: (row) => row.id,
          sortable: true
        }
      ],
      selected: [],
      selectedLesson: [],
      selectedRoom: [],
      selectedDay: [
        { label: this.$t('entity.appointment.mon'), value: 1 },
        { label: this.$t('entity.appointment.thu'), value: 2 },
        { label: this.$t('entity.appointment.wed'), value: 3 },
        { label: this.$t('entity.appointment.tue'), value: 4 },
        { label: this.$t('entity.appointment.fri'), value: 5 },
        { label: this.$t('entity.appointment.sat'), value: 6 },
        { label: this.$t('entity.appointment.sun'), value: 7 }
      ],
      selectedTime: [
        { label: this.$t('time.one'), value: 1 },
        { label: this.$t('time.two'), value: 2 },
        { label: this.$t('time.three'), value: 3 },
        { label: this.$t('time.four'), value: 4 },
        { label: this.$t('time.five'), value: 5 },
        { label: this.$t('time.six'), value: 6 },
        { label: this.$t('time.seven'), value: 7 },
        { label: this.$t('time.eight'), value: 8 },
        { label: this.$t('time.nine'), value: 9 },
        { label: this.$t('time.ten'), value: 10 },
        { label: this.$t('time.eleven'), value: 11 }
      ]
    }
  },
  created () {
    this.load()
    userStore().load()
  },
  methods: {
    load () {
      this.$api.get('/getAllLesson').then(res => {
        // console.log(res)
        if (res.data.code === 200) {
          this.rows = res.data.datas
          this.selectedLesson = []
          this.rows.forEach((item) => {
            this.selectedLesson.push({
              label: item.name,
              value: item.id
            })
          })
        }
      })
      this.$api.get('/getAllClassroom').then(res => {
        // console.log(res)
        if (res.data.code === 200) {
          this.selectedRoom = []
          res.data.datas.forEach((item) => {
            this.selectedRoom.push({
              label: item.tower + item.num,
              value: item.id
            })
          })
        }
      })
    },
    addLesson () {
      this.$api.post('/addLesson', [this.data]).then(res => {
        // console.log(res)
        if (res.data.code === 200) {
          this.load()
          this.addbtn = false
          this.data.name = ''
        }
      })
    },
    deleteLesson () {
      this.$api.post('/deleteLesson', this.selected).then(res => {
        // console.log(res)
        if (res.data.code === 200) {
          this.load()
        }
      })
    },
    addTimetable () {
      let timetables = []
      if (this.timetable.time.length <= 1) {
        this.timetable.day = [this.timetable.day]
        timetables = [this.timetable]
      } else {
        this.timetable.time.forEach((item) => {
          timetables.push({
            lessonId: this.timetable.lessonId,
            activeId: -1,
            roomId: this.timetable.roomId,
            day: [this.timetable.day],
            time: [item],
            startDate: this.timetable.startDate,
            endDate: this.timetable.endDate
          })
        })
      }
      // console.log(timetables)
      this.$api.post('/addTimetable', timetables).then(res => {
        if (res.data.code === 200) {
          this.$q.dialog({ message: 'success' })
          this.timetable = {
            lessonId: '',
            activeId: -1,
            roomId: '',
            day: '',
            time: [],
            startDate: '',
            endDate: ''
          }
        }
      })
    }
  }
})
</script>
