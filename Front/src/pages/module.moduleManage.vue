<template>
  <!-- Table -->
  <div class="q-pa-md">
    <q-table :title="$t('module.moduleManage.name')"
    :rows="rows"
    :columns="columns"
    row-key="id"
    binary-state-sort
    :filter="filter"
    virtual-scroll
    style="max-height: 600px"
    v-model:pagination="pagination"
    :rows-per-page-options="[0]"
    :no-data-label="$t('table.nodata')"
    :no-results-label="$t('table.noresult')"
    hide-bottom
    separator="cell">
      <template v-slot:top-right>
        <q-select borderless dense debounce="300" v-model="filter" :options="option">
        </q-select>
      </template>
    </q-table>
  </div>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'moduleManage',
  data () {
    return {
      filter: '',
      option: ['', 'host', 'manage', 'student'],
      pagination: {
        rowsPerPage: 0
      },
      rows: [],
      tmp: { 1: 'host', 2: 'manage', 3: 'student' },
      columns: [
        {
          name: 'moduleName',
          required: true,
          label: this.$t('entity.module.name'),
          align: 'left',
          field: (row) => this.$t(row.moduleName + '.name'),
          sortable: true
        },
        {
          name: 'route',
          align: 'left',
          label: this.$t('entity.module.route'),
          field: (row) => row.route
        },
        {
          name: 'icon',
          align: 'left',
          label: this.$t('entity.module.icon'),
          field: (row) => row.icon
        },
        {
          name: 'color',
          align: 'left',
          label: this.$t('entity.module.color'),
          field: (row) => row.color
        },
        {
          name: 'authority',
          align: 'left',
          label: this.$t('entity.module.authority'),
          field: (row) => this.$t('authority.' + this.tmp[row.authority])
        }
      ]
    }
  },
  created () {
    this.load()
  },
  methods: {
    load () {
      this.$api.get('/getAllModule').then(res => {
        // console.log(res)
        if (res.data.code === 200) {
          this.rows = res.data.datas
          this.columns = this.columns.filter((res) => {
            return res.name !== 'password'
          })
        }
      })
    }
  }
})
</script>
