<template>
  <div class="q-pa-md">
    <q-table :title="$t('module.appointment.name')" :rows="rows" :columns="columns" row-key="id" binary-state-sort
        virtual-scroll
      v-model:pagination="pagination" :rows-per-page-options="[0]" :no-data-label="$t('table.nodata')"
      :no-results-label="$t('table.noresult')" separator="cell"
      :wrap-cells='true' :table-colspan='8'>
      <template v-slot:top-right>
        <q-select
          v-model="classroom"
          outlined
          dense
          emit-value
          map-options
          :options="selectClassroom"
          @update:model-value="loadTimetable()"
        />
      </template>
      <template v-slot:bottom>
        <q-btn-group push >
        <q-btn color="primary" :label="$t('module.appointment.feature.add')" @click="addActivebtn = true" />
        </q-btn-group>
        <q-btn
          icon="chevron_left"
          color="grey-8"
          round
          dense
          flat
          @click="lastweek"
        />
        <q-btn
        icon="circle"
        color="grey-8"
        round
        dense
        flat
        @click="loadThisWeekTimetable"
      />
        <q-btn
          icon="chevron_right"
          color="grey-8"
          round
          dense
          flat
          @click="nextweek()"
        />
      </template>

    </q-table>
  </div>
  <!-- add active -->
  <q-dialog v-model="addActivebtn" persistent v-if="user.authorityId === 'host'">
    <q-card style="min-width: 350px">
      <q-card-section>
        <div class="text-h6">{{ this.$t("module.appointment.feature.add") }}</div>
      </q-card-section>

      <q-card-section class="q-gutter-y-md">
        <q-input filled v-model="active.name" :label="$t('entity.active.name')" />
        <q-select readonly :map-options="true" :emit-value="true" filled v-model="classroom" :options="selectClassroom" :label="$t('entity.active.roomId')" />
        <q-select :map-options="true" :emit-value="true" :multiple="true" filled v-model="active.time" :options="selectedTime" :label="$t('entity.active.time')" />
        <q-input filled v-model="active.date" mask="####-##-##" :label="$t('entity.active.date')">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                <q-date v-model="active.date">
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
        <q-btn flat :label="$t('button.save')" @click="addActive()"/>
        <q-btn flat :label="$t('button.cancel')" v-close-popup/>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { userStore } from 'src/stores/user-store'
export default {
  name: 'AppointmentPage',
  data () {
    const thisweek = this.getThisWeekData()

    return {
      addActivebtn: false,
      time: thisweek,
      classroom: '1',
      next: 0,
      pre: 0,
      active: {
        name: '',
        principalId: '',
        createDate: '',
        accept: false,
        roomId: '',
        date: '',
        time: []
      },
      selectClassroom: [],
      pagination: {
        rowsPerPage: 12
      },
      rows: [{
        time: '8:10-8:55',
        mon: {
          name: 'SE',
          classroom: '6A101',
          principal: 'xxx',
          color: '#ffeedd'
        },
        tue: {
          name: 'SE',
          classroom: '6A101',
          principal: 'xxx',
          color: '#66ccff'
        },
        wed: {
          name: 'SE',
          classroom: '6A101',
          principal: 'xxx',
          color: '#33eeff'
        },
        thu: {
          name: 'SE',
          classroom: '6A101',
          principal: 'xxx',
          color: '#ffffff'
        },
        fri: {
          name: 'SE',
          classroom: '6A101',
          principal: 'xxx',
          color: '#336644'
        },
        sat: {
          name: '',
          classroom: '',
          principal: '',
          color: ''
        },
        sun: {
          name: '',
          classroom: '',
          principal: '',
          color: ''
        }
      }],
      user: userStore().user,
      columns: [
        {
          name: 'blank',
          label: this.$t('entity.appointment.blank'),
          align: 'center',
          field: (row) => row.time,
          style: 'width:50px;height:100px;background-color:#ffeebb'
        },
        {
          name: 'mon',
          align: 'center',
          label: this.$t('entity.appointment.mon') + thisweek.mon,
          field: (row) => row.mon.name ? row.mon.name + ' @' + row.mon.classroom + ' :' + row.mon.principal : '',
          style: row => ('width: 100px;background-color: ' + row.mon.color)
        },
        {
          name: 'tue',
          align: 'center',
          label: this.$t('entity.appointment.tue') + thisweek.tue,
          field: (row) => row.tue.name ? row.tue.name + ' @' + row.tue.classroom + ' :' + row.tue.principal : '',
          style: row => ('width: 100px;background-color: ' + row.tue.color)
        },
        {
          name: 'wed',
          align: 'center',
          label: this.$t('entity.appointment.wed') + thisweek.wed,
          field: (row) => row.wed.name ? row.wed.name + ' @' + row.wed.classroom + ' :' + row.wed.principal : '',
          style: row => ('width: 100px;background-color: ' + row.wed.color)
        },
        {
          name: 'thu',
          align: 'center',
          label: this.$t('entity.appointment.thu') + thisweek.thu,
          field: (row) => row.thu.name ? row.thu.name + ' @' + row.thu.classroom + ' :' + row.thu.principal : '',
          style: row => ('width: 100px;background-color: ' + row.thu.color)
        },
        {
          name: 'fri',
          align: 'center',
          label: this.$t('entity.appointment.fri') + thisweek.fri,
          field: (row) => row.fri.name ? row.fri.name + ' @' + row.fri.classroom + ' :' + row.fri.principal : '',
          style: row => ('width: 100px;background-color: ' + row.fri.color)
        },
        {
          name: 'sat',
          align: 'center',
          label: this.$t('entity.appointment.sat') + thisweek.sat,
          field: (row) => row.sat.name ? row.sat.name + ' @' + row.sat.classroom + ' :' + row.sat.principal : '',
          style: row => ('width: 100px;background-color: ' + row.sat.color)
        },
        {
          name: 'sun',
          align: 'center',
          label: this.$t('entity.appointment.sun') + thisweek.sun,
          field: (row) => row.sun.name ? row.sun.name + ' @' + row.sun.classroom + ' :' + row.sun.principal : '',
          style: row => ('width: 100px;background-color: ' + row.sun.color)
        }
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
  },
  beforeMount () {
    this.$api.get('/getAllClassroom').then((res) => {
      // console.log(res)
      if (res.data.code === 200) {
        res.data.datas.forEach((item) => {
          this.selectClassroom.push({
            label: item.tower + item.num,
            value: item.id
          })
        })
        this.classroom = this.selectClassroom.at(0).value
      }
    })
  },
  mounted () {
    this.loadTimetable()
  },
  methods: {
    loadTimetable () {
      const data = [this.time.mon, this.time.sun, this.classroom]
      // console.log(data)
      this.$api.post('/defaultTimetable', data).then(res => {
        // console.log(res)
        if (res.data.code === 200) {
          this.rows = res.data.datas
        }
      })
    },
    getThisWeekData () {
      // 获得本周周一~周日的年月日
      const thisweek = {}
      const date = new Date()
      // 本周一的日期
      date.setDate(date.getDate() - date.getDay() + 1)
      thisweek.mon = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

      date.setDate(date.getDate() + 1)
      thisweek.tue = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

      date.setDate(date.getDate() + 1)
      thisweek.wed = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

      date.setDate(date.getDate() + 1)
      thisweek.thu = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

      date.setDate(date.getDate() + 1)
      thisweek.fri = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

      date.setDate(date.getDate() + 1)
      thisweek.sat = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()
      // 本周日的日期
      date.setDate(date.getDate() + 1)
      thisweek.sun = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()
      return thisweek
    },
    addActive () {
      this.active.createDate = new Date()
      this.active.principalId = this.user.id
      this.active.roomId = this.classroom
      this.$api.post('/addActive', [this.active]).then(res => {
        if (res.data.code === 200) {
          this.loadTimetable()
          this.$q.dialog({ message: this.$t('result.success.accept') })
        }
      })
    },
    loadThisWeekTimetable () {
      this.next = 0
      this.pre = 0
      this.time = this.getThisWeekData()
      this.columns = [
        {
          name: 'blank',
          label: this.$t('entity.appointment.blank'),
          align: 'center',
          field: (row) => row.time,
          style: 'width:50px;height:100px;background-color:#ffeebb'
        },
        {
          name: 'mon',
          align: 'center',
          label: this.$t('entity.appointment.mon') + this.time.mon,
          field: (row) => row.mon.name ? row.mon.name + ' @' + row.mon.classroom + ' :' + row.mon.principal : '',
          style: row => ('width: 100px;background-color: ' + row.mon.color)
        },
        {
          name: 'tue',
          align: 'center',
          label: this.$t('entity.appointment.tue') + this.time.tue,
          field: (row) => row.tue.name ? row.tue.name + ' @' + row.tue.classroom + ' :' + row.tue.principal : '',
          style: row => ('width: 100px;background-color: ' + row.tue.color)
        },
        {
          name: 'wed',
          align: 'center',
          label: this.$t('entity.appointment.wed') + this.time.wed,
          field: (row) => row.wed.name ? row.wed.name + ' @' + row.wed.classroom + ' :' + row.wed.principal : '',
          style: row => ('width: 100px;background-color: ' + row.wed.color)
        },
        {
          name: 'thu',
          align: 'center',
          label: this.$t('entity.appointment.thu') + this.time.thu,
          field: (row) => row.thu.name ? row.thu.name + ' @' + row.thu.classroom + ' :' + row.thu.principal : '',
          style: row => ('width: 100px;background-color: ' + row.thu.color)
        },
        {
          name: 'fri',
          align: 'center',
          label: this.$t('entity.appointment.fri') + this.time.fri,
          field: (row) => row.fri.name ? row.fri.name + ' @' + row.fri.classroom + ' :' + row.fri.principal : '',
          style: row => ('width: 100px;background-color: ' + row.fri.color)
        },
        {
          name: 'sat',
          align: 'center',
          label: this.$t('entity.appointment.sat') + this.time.sat,
          field: (row) => row.sat.name ? row.sat.name + ' @' + row.sat.classroom + ' :' + row.sat.principal : '',
          style: row => ('width: 100px;background-color: ' + row.sat.color)
        },
        {
          name: 'sun',
          align: 'center',
          label: this.$t('entity.appointment.sun') + this.time.sun,
          field: (row) => row.sun.name ? row.sun.name + ' @' + row.sun.classroom + ' :' + row.sun.principal : '',
          style: row => ('width: 100px;background-color: ' + row.sun.color)
        }
      ]
      this.loadTimetable()
    },
    nextweek () {
      // 获得本周周一~周日的年月日
      const thisweek = {}
      const date = new Date()
      this.next++
      const total = this.next - this.pre
      if (total === 0) {
        this.next = 0
        this.pre = 0
        this.loadThisWeekTimetable()
      } else {
        // 本周一的日期
        date.setDate(date.getDate() - date.getDay() + 1 + total * 7)
        thisweek.mon = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.tue = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.wed = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.thu = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.fri = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.sat = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()
        // 本周日的日期
        date.setDate(date.getDate() + 1)
        thisweek.sun = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()
        this.time = thisweek
        this.loadTimetable()
        this.columns = [
          {
            name: 'blank',
            label: this.$t('entity.appointment.blank'),
            align: 'center',
            field: (row) => row.time,
            style: 'width:50px;height:100px;background-color:#ffeebb'
          },
          {
            name: 'mon',
            align: 'center',
            label: this.$t('entity.appointment.mon') + thisweek.mon,
            field: (row) => row.mon.name ? row.mon.name + ' @' + row.mon.classroom + ' :' + row.mon.principal : '',
            style: row => ('width: 100px;background-color: ' + row.mon.color)
          },
          {
            name: 'tue',
            align: 'center',
            label: this.$t('entity.appointment.tue') + thisweek.tue,
            field: (row) => row.tue.name ? row.tue.name + ' @' + row.tue.classroom + ' :' + row.tue.principal : '',
            style: row => ('width: 100px;background-color: ' + row.tue.color)
          },
          {
            name: 'wed',
            align: 'center',
            label: this.$t('entity.appointment.wed') + thisweek.wed,
            field: (row) => row.wed.name ? row.wed.name + ' @' + row.wed.classroom + ' :' + row.wed.principal : '',
            style: row => ('width: 100px;background-color: ' + row.wed.color)
          },
          {
            name: 'thu',
            align: 'center',
            label: this.$t('entity.appointment.thu') + thisweek.thu,
            field: (row) => row.thu.name ? row.thu.name + ' @' + row.thu.classroom + ' :' + row.thu.principal : '',
            style: row => ('width: 100px;background-color: ' + row.thu.color)
          },
          {
            name: 'fri',
            align: 'center',
            label: this.$t('entity.appointment.fri') + thisweek.fri,
            field: (row) => row.fri.name ? row.fri.name + ' @' + row.fri.classroom + ' :' + row.fri.principal : '',
            style: row => ('width: 100px;background-color: ' + row.fri.color)
          },
          {
            name: 'sat',
            align: 'center',
            label: this.$t('entity.appointment.sat') + thisweek.sat,
            field: (row) => row.sat.name ? row.sat.name + ' @' + row.sat.classroom + ' :' + row.sat.principal : '',
            style: row => ('width: 100px;background-color: ' + row.sat.color)
          },
          {
            name: 'sun',
            align: 'center',
            label: this.$t('entity.appointment.sun') + thisweek.sun,
            field: (row) => row.sun.name ? row.sun.name + ' @' + row.sun.classroom + ' :' + row.sun.principal : '',
            style: row => ('width: 100px;background-color: ' + row.sun.color)
          }
        ]
      }
    },
    lastweek () {
      const thisweek = {}
      const date = new Date()
      this.pre++
      const total = this.next - this.pre
      if (total === 0) {
        this.next = 0
        this.pre = 0
        this.loadThisWeekTimetable()
      } else {
        // 本周一的日期
        date.setDate(date.getDate() - date.getDay() + 1 + total * 7)
        thisweek.mon = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.tue = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.wed = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.thu = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.fri = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()

        date.setDate(date.getDate() + 1)
        thisweek.sat = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()
        // 本周日的日期
        date.setDate(date.getDate() + 1)
        thisweek.sun = date.getFullYear() + ((date.getMonth() + 1) < 10 ? '-0' : '-') + (date.getMonth() + 1) + (date.getDate() < 10 ? '-0' : '-') + date.getDate()
        this.time = thisweek
        this.loadTimetable()
        this.columns = [
          {
            name: 'blank',
            label: this.$t('entity.appointment.blank'),
            align: 'center',
            field: (row) => row.time,
            style: 'width:50px;height:100px;background-color:#ffeebb'
          },
          {
            name: 'mon',
            align: 'center',
            label: this.$t('entity.appointment.mon') + thisweek.mon,
            field: (row) => row.mon.name ? row.mon.name + ' @' + row.mon.classroom + ' :' + row.mon.principal : '',
            style: row => ('width: 100px;background-color: ' + row.mon.color)
          },
          {
            name: 'tue',
            align: 'center',
            label: this.$t('entity.appointment.tue') + thisweek.tue,
            field: (row) => row.tue.name ? row.tue.name + ' @' + row.tue.classroom + ' :' + row.tue.principal : '',
            style: row => ('width: 100px;background-color: ' + row.tue.color)
          },
          {
            name: 'wed',
            align: 'center',
            label: this.$t('entity.appointment.wed') + thisweek.wed,
            field: (row) => row.wed.name ? row.wed.name + ' @' + row.wed.classroom + ' :' + row.wed.principal : '',
            style: row => ('width: 100px;background-color: ' + row.wed.color)
          },
          {
            name: 'thu',
            align: 'center',
            label: this.$t('entity.appointment.thu') + thisweek.thu,
            field: (row) => row.thu.name ? row.thu.name + ' @' + row.thu.classroom + ' :' + row.thu.principal : '',
            style: row => ('width: 100px;background-color: ' + row.thu.color)
          },
          {
            name: 'fri',
            align: 'center',
            label: this.$t('entity.appointment.fri') + thisweek.fri,
            field: (row) => row.fri.name ? row.fri.name + ' @' + row.fri.classroom + ' :' + row.fri.principal : '',
            style: row => ('width: 100px;background-color: ' + row.fri.color)
          },
          {
            name: 'sat',
            align: 'center',
            label: this.$t('entity.appointment.sat') + thisweek.sat,
            field: (row) => row.sat.name ? row.sat.name + ' @' + row.sat.classroom + ' :' + row.sat.principal : '',
            style: row => ('width: 100px;background-color: ' + row.sat.color)
          },
          {
            name: 'sun',
            align: 'center',
            label: this.$t('entity.appointment.sun') + thisweek.sun,
            field: (row) => row.sun.name ? row.sun.name + ' @' + row.sun.classroom + ' :' + row.sun.principal : '',
            style: row => ('width: 100px;background-color: ' + row.sun.color)
          }
        ]
      }
    }
  }

}
</script>
