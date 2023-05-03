<template>
  <!-- Table -->
  <div class="q-pa-md">
    <q-table :title="$t('module.manage.name')" :rows="rows" :columns="columns" row-key="id" binary-state-sort
      selection="multiple" v-model:selected="selected" :filter="filter" virtual-scroll style="max-height: 600px"
      v-model:pagination="pagination" :rows-per-page-options="[0]" :no-data-label="$t('table.nodata')"
      :no-results-label="$t('table.noresult')" separator="cell">
      <template v-slot:bottom>
        <q-btn-group push>
        <q-btn color="primary" :label="$t('module.manage.feature.addUser')" @click="addbtn = true" />
        <q-btn color="grey-9" :label="$t('module.manage.feature.deleteUsers')" @click="deleteuser()" />
        <q-btn color="yellow-9" :label="$t('module.manage.feature.editUsers')" @click="editUser()" />
        </q-btn-group>
        <q-btn-group push v-if="this.user.authorityId==='host'">
          <q-btn color="primary" :label="$t('module.manage.feature.addManyUser')" @click="importbtn=true"/>
          <q-btn color="grey-9" :label="$t('module.manage.feature.exportUser')" @click="exportTable()"/>
          <q-btn color="yellow-9" :label="$t('module.manage.feature.exportNoTable')" @click="exportBlankTable()" />
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
  <!-- Add User -->
  <q-dialog v-model="addbtn" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div class="text-h6">{{ this.$t("module.manage.newUser") }}</div>
      </q-card-section>

      <q-card-section class="q-gutter-y-md">
        <q-input :rules="errorname" outlined v-model="data.username" :label="$t('entity.user.name')" />
        <q-input outlined :rules="erroraccount" v-model="data.email" :label="$t('entity.user.account')" />
        <q-input outlined :rules="errorpassword" v-model="data.password" :label="$t('entity.user.password')" />
        <q-select outlined v-if="user.authorityId === 'host'" v-model="data.authorityId" :options="authOptions"
        :label="$t('entity.user.authority')" />
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat :label="$t('button.save')" @click="add" />
        <q-btn flat :label="$t('button.cancel')" v-close-popup @click="cancelbtn"/>
      </q-card-actions>
    </q-card>
  </q-dialog>
  <!-- Edit -->
  <q-dialog v-model="editDialog" persistent>
  <div>
    <q-btn-group push>
      <q-btn color="primary" :label="$t('button.submit')" @click="updateEdit" />
      <q-btn color="grey-9" :label="$t('button.cancel')" @click="cancelEdit" />
    </q-btn-group>
    <q-btn-group push v-if="this.user.authorityId==='host'">
      <q-btn color="pink" :label="$t('module.manage.feature.setManage')" @click="setManage"/>
      <q-btn color="green" :label="$t('module.manage.feature.setStudent')" @click="setStudent"/>
    </q-btn-group>
    <q-carousel transition-prev="scale" transition-next="scale" swipeable animated v-model="slide"
      control-color="primary" navigation-icon="radio_button_unchecked" arrows padding height="400px"
      class="bg-white shadow-1 rounded-borders">
      <q-carousel-slide v-for="(user,index) in selected" :key="index" :name="user.id" class="column no-wrap flex-center">
          <q-card-section>
            <div class="text-h6">{{ user.username }}</div>
          </q-card-section>
          <q-card-section class="q-gutter-y-md">
            <q-input outlined :rules="errorname" v-model="user.username" :label="$t('entity.user.name')" />
            <q-input outlined :rules="erroraccount" v-model="user.email" :label="$t('entity.user.account')" />
            <q-select outlined v-if="this.user.authorityId === 'host' && user.id!==1" v-model="user.authorityId" :options="authOptions"
            :label="$t('entity.user.authority')" />
          </q-card-section>
      </q-carousel-slide>
    </q-carousel>
  </div>
  </q-dialog>
  <!-- Import -->
  <q-dialog v-model="importbtn" persistent transition-show="scale" transition-hide="scale">
    <q-card class="bg-teal text-white" style="width: 300px">
      <q-card-section>
        <div class="text-h6">{{this.$t('import.file')}}</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-file filled v-model="userfile" :label="$t('import.file')" accept=".csv"/>
      </q-card-section>

      <q-card-actions align="right" class="bg-white text-teal">
        <q-btn flat :label="$t('button.submit')" @click="importFile" />
        <q-btn color="blue" flat :label="$t('button.cancel')" v-close-popup @click="(userfile=null)"/>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { userStore } from 'src/stores/user-store'
import { exportFile } from 'quasar'

export default {
  name: 'UserManage',
  data () {
    return {
      // Input rule
      errorname: [val => val.length >= 2 || this.$t('input.error.lengthS'),
        val => val.length <= 15 || this.$t('input.error.lengthL')],
      erroraccount: [val => val.length >= 5 || this.$t('input.error.lengthS'),
        val => val.length <= 15 || this.$t('input.error.lengthL'),
        val => !isNaN(val) || this.$t('input.error.onlyNum')],
      errorpassword: [val => val.length >= 5 || this.$t('input.error.lengthS'),
        val => val.length <= 20 || this.$t('input.error.lengthL')],
      // dialog btn
      addbtn: false,
      editDialog: false,
      importbtn: false,
      // casol
      slide: '1',
      // choose file
      userfile: null,
      // option
      authOptions: ['student', 'manage'],
      // table
      filter: '',
      pagination: {
        rowsPerPage: 0
      },
      selected: [],
      rows: [],
      columns: [
        {
          name: 'name',
          required: true,
          label: this.$t('entity.user.name'),
          align: 'left',
          field: (row) => row.username,
          sortable: true
        },
        {
          name: 'id',
          align: 'left',
          label: this.$t('entity.user.id'),
          field: (row) => row.id,
          sortable: true
        },
        {
          name: 'email',
          align: 'left',
          label: this.$t('entity.user.account'),
          field: (row) => row.email
        },
        {
          name: 'password',
          align: 'left',
          label: this.$t('entity.user.password'),
          field: (row) => row.password
        },
        {
          name: 'authorityId',
          align: 'left',
          label: this.$t('entity.user.authority'),
          field: (row) => this.$t('authority.' + row.authorityId)
        }
      ],
      // user
      user: userStore().user,
      data: {
        username: '',
        email: '',
        authorityId: 'student',
        password: ''
      }
    }
  },
  created () {
    userStore().load()
  },
  mounted () {
    this.load()
  },
  methods: {
    // 读取用户信息
    load () {
      this.$api.get('/getAllUser').then((res) => {
      // console.log(res)
        if (res.data.code === 200) {
          this.rows = res.data.datas
          this.columns = this.columns.filter((res) => {
            return res.name !== 'password'
          })
          if (this.user.authorityId !== 'host') {
            this.rows = this.rows.filter((res) => {
              return res.id !== 1
            })
          }
        }
      })
    },
    // 添加用户
    add () {
      // console.log(this.data)
      const res = this.data
      // 错误：数据不符合规范
      if (res.username === undefined || res.username.length < 2 || res.username.length > 15) {
        this.$q.dialog({ message: this.$t('result.error.format') })
        return
      }
      if (res.email === undefined || res.email.length < 5 || res.email.length > 15 || isNaN(res.email)) {
        this.$q.dialog({ message: this.$t('result.error.format') })
        return
      }
      if (res.password === undefined || res.password.length < 5 || res.password.length > 20) {
        this.$q.dialog({ message: this.$t('result.error.format') })
        return
      }
      if (res.authorityId === undefined || !(res.authorityId === 'manage' || res.authorityId === 'student')) {
        this.$q.dialog({ message: this.$t('result.error.format') })
        return
      }

      const data = [this.data]
      this.$api.post('/addUser', data).then(res => {
        // console.log(res)
        if (res.data.code === 200) {
          if (res.data.datas) {
            this.load()
            this.addbtn = false
            this.data = {
              username: '',
              email: '',
              authorityId: 'student',
              password: ''
            }
            // 成功：添加用户成功
            this.$q.dialog({ message: this.$t('result.success.add') })
          } else {
            // 错误：用户已存在
            this.$q.dialog({ message: this.$t('result.error.add') })
          }
        }
      })
    },
    cancelbtn () {
      this.data = {
        username: '',
        email: '',
        authorityId: 'student',
        password: ''
      }
    },
    // 删除用户
    deleteuser () {
      try {
        if (this.user.authorityId === 'manage') {
          this.selected.forEach(res => {
            if (res.authorityId === 'manage') {
              // 错误：不能操作管理员数据
              this.$q.dialog({ message: this.$t('result.error.auth') })
              throw new Error('line')
            }
          })
        }
        this.$api.post('/deleteUser', this.selected).then(res => {
          // console.log(res)
          if (res.data.code === 200) {
            if (!res.data.datas) {
              // 错误：删除失败
              this.$q.dialog({ message: this.$t('result.error.delete') })
            } else {
              // 成功：删除成功
              this.$q.dialog({ message: this.$t('result.success.delete') })
              this.load()
            }
          }
        })
      } catch (e) {
        if (e.message !== 'line') throw e
      }
    },
    // 编辑用户
    editUser () {
      try {
        if (this.user.authorityId === 'manage') {
          this.selected.forEach(res => {
            if (res.authorityId === 'manage') {
              // 错误：不能操作管理员数据
              this.$q.dialog({ message: this.$t('result.error.auth') })
              throw new Error('line')
            }
          })
        }
        if (this.selected.length === 0) {
          // 错误：必须选中数据进行操作
          this.$q.dialog({ message: this.$t('result.error.noSelect') })
        } else if (this.selected.length > 20) {
          // 错误：操作数据过多
          this.$q.dialog({ message: this.$t('result.error.manySelect') })
        } else {
          this.slide = this.selected[0].id
          this.editDialog = true
        }
      } catch (e) {
        if (e.message !== 'line') throw e
      }
    },
    cancelEdit () {
      this.editDialog = false
      this.load()
      this.selected = []
    },
    setManage () {
      this.selected.forEach(res => {
        if (res.id !== 1) {
          res.authorityId = 'manage'
        }
        return res
      })
    },
    setStudent () {
      this.selected.forEach(res => {
        if (res.id !== 1) {
          res.authorityId = 'student'
        }
        return res
      })
    },
    updateEdit () {
      try {
        // 错误：提交的数据存在问题
        this.selected.forEach((res) => {
          if (res.username === undefined || res.username.length < 2 || res.username.length > 15) {
            this.$q.dialog({ message: this.$t('result.error.format') })
            throw new Error('line')
          }
          if (res.email === undefined || res.email.length < 5 || res.email.length > 15 || isNaN(res.email)) {
            this.$q.dialog({ message: this.$t('result.error.format') })
            throw new Error('line')
          }
          if (res.authorityId === undefined || !(res.authorityId === 'manage' || res.authorityId === 'student' || res.authorityId === 'host')) {
            this.$q.dialog({ message: this.$t('result.error.format') })
            throw new Error('line')
          }
        }
        )
        this.$api.post('updateUser', this.selected).then(res => {
          console.log(res)
          if (res.data.code === 200) {
            if (res.data.datas) {
              this.load()
              this.editDialog = false
              // 成功：修改成功
              this.$q.dialog({ message: this.$t('result.success.update') })
              this.selected = []
            } else {
              // 错误：修改失败
              this.$q.dialog({ message: this.$t('result.error.update') })
            }
          }
        })
      } catch (e) {
        if (e.message !== 'line') throw e
      }
    },
    // 批量添加用户
    // 导出已有用户数据
    wrapCsvValue (val, formatFn, row) {
      let formatted = formatFn !== void 0
        ? formatFn(val, row)
        : val

      formatted = formatted === void 0 || formatted === null
        ? ''
        : String(formatted)

      formatted = formatted.split('"').join('""')
      /**
   * Excel accepts \n and \r in strings, but some other CSV parsers do not
   * Uncomment the next two lines to escape new lines
   */
      // .split('\n').join('\\n')
      // .split('\r').join('\\r')

      return `"${formatted}"`
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => this.wrapCsvValue(col.label))].concat(
        this.rows.map(row => this.columns.map(col => this.wrapCsvValue(
          typeof col.field === 'function'
            ? col.field(row)
            : row[col.field === void 0 ? col.name : col.field],
          col.format,
          row
        )).join(','))
      ).join('\r\n')

      const status = exportFile(
        'userinfo.csv',
        content,
        'text/csv'
      )

      if (status !== true) {
        this.$q.notify({
          message: 'Browser denied file download...',
          color: 'negative',
          icon: 'warning'
        })
      }
    },
    // 导出基本表格格式
    exportBlankTable () {
      const content = ['username', 'email', 'password', 'authorityId']
      exportFile(
        'userbasic.csv',
        content,
        'text/csv'
      )
    },
    // 选择批量添加用户数据的表格
    importFile () {
      if (this.userfile != null) {
        const reader = new FileReader()
        reader.readAsText(this.userfile)
        reader.onload = () => {
          const csv = reader.result
          const array = csv.toString().split('\r\n')
          /* Store the converted result into an array */
          const csvToJsonResult = []
          /* Store the CSV column headers into seprate variable */
          const headers = array[0].split(',')
          /* Iterate over the remaning data rows */
          for (let i = 1; i < array.length - 1; i++) {
            /* Empty object to store result in key value pair */
            const jsonObject = {}
            /* Store the current array element */
            const currentArrayString = array[i]
            let string = ''
            let quoteFlag = 0
            for (let character of currentArrayString) {
              if (character === '"' && quoteFlag === 0) quoteFlag = 1
              else if (character === '"' && quoteFlag === 1) quoteFlag = 0
              if (character === ',' && quoteFlag === 0) character = '|'
              if (character !== '"') string += character
            }
            const jsonProperties = string.split('|')
            for (const j in headers) {
              if (jsonProperties[j].includes(',')) {
                jsonObject[headers[j]] = jsonProperties[j]
                  .split(',').map(item => item.trim())
              } else jsonObject[headers[j]] = jsonProperties[j]
            }
            /* Push the genearted JSON object to resultant array */
            csvToJsonResult.push(jsonObject)
          }
          /* Convert the final array to JSON */
          const json = csvToJsonResult
          if (json.length < 1) {
            this.importbtn = false
            this.userfile = null
            // 错误：数据不符合标准
            this.$q.dialog({ message: this.$t('result.error.format') })
          } else {
            // 错误：数据x行存在问题
            try {
              json.forEach((res, index) => {
                if (res.username === undefined || res.username.length < 2 || res.username.length > 15) {
                  this.$q.dialog({ message: this.$t('result.error.line', [index + 1]) })
                  throw new Error('line')
                }
                if (res.email === undefined || res.email.length < 5 || res.email.length > 15 || isNaN(res.email)) {
                  this.$q.dialog({ message: this.$t('result.error.line', [index + 1]) })
                  throw new Error('line')
                }
                if (res.password === undefined || res.password.length < 5 || res.password.length > 20) {
                  this.$q.dialog({ message: this.$t('result.error.line', [index + 1]) })
                  throw new Error('line')
                }
                if (res.authorityId === undefined || !(res.authorityId === 'manage' || res.authorityId === 'student')) {
                  this.$q.dialog({ message: this.$t('result.error.line', [index + 1]) })
                  throw new Error('line')
                }
              })
              this.$api.post('/addUser', json).then(res => {
                if (res.data.code === 200) {
                  if (res.data.datas) {
                    // 成功：导入成功
                    this.$q.dialog({ message: this.$t('result.success.import') })
                    this.load()
                  } else {
                    // 失败：导入失败
                    this.$q.dialog({ message: this.$t('result.error.import') })
                  }
                }
              })
            } catch (e) {
              if (e.message !== 'line') throw e
            }
            this.userfile = null
            this.importbtn = false
          }
        }
      } else {
        // 错误：请选择文件
        this.$q.dialog({ message: this.$t('result.error.noSelect') })
      }
    }
  }
}
</script>
