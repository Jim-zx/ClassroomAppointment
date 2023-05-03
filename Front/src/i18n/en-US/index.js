// This is just an example,
// so you can safely delete all default props below

export default {
  failed: 'Action failed',
  success: 'Action was successful',
  title: 'Classroom Appointment System',
  import: {
    file: 'Choose File'
  },
  result: {
    success: {
      accept: 'Active Accept',
      add: 'Add Success',
      delete: 'Delete Success',
      update: 'Update Success',
      import: 'Import Success'
    },
    error: {
      add: 'Add Failed',
      delete: 'Delete Failed',
      update: 'Update Failed',
      import: 'Import Failed',
      format: 'Datas Format Wrong',
      exist: 'Data Already Exist',
      auth: 'Permission Denied',
      noSelect: 'No Data Choose',
      manySelect: 'Many Data Choose',
      line: 'line {0} have problem'
    }
  },
  input: {
    error: {
      lengthS: 'Too short',
      lengthL: 'Too long',
      onlyChar: 'Can only type char',
      onlyNum: 'Can only type number'
    }
  },
  table: {
    nodata: 'No Data Record',
    noresult: 'No Matching Data Record'
  },
  user: {
    account: 'Account',
    password: 'Password'
  },
  button: {
    submit: 'Submit',
    cancel: 'Cancel',
    save: 'Save'
  },
  auth: {
    success: 'Login Success',
    fail: 'Account or Password wrong'
  },
  module: {
    manage: {
      name: 'User Manage',
      newUser: 'New User',
      feature: {
        addUser: 'Add User',
        deleteUsers: 'Delete Users',
        editUsers: 'Edit Users',
        setManage: 'All Set To Manage',
        setStudent: 'All Set To Student',
        addManyUser: 'Import Users',
        exportUser: 'Export Users',
        exportNoTable: 'Export Blank Table'
      }
    },
    classroom: {
      name: 'Classroom Manage',
      feature: {
        add: 'Add Classroom',
        delete: 'Delete Classroom',
        edit: 'Edit Classroom'
      }
    },
    appointment: {
      name: 'Classroom Appointment',
      feature: {
        add: 'Appointment Active'
      }
    },
    moduleManage: {
      name: 'Module Check',
      feature: {
        addModule: 'Add Module',
        deleteModule: 'Delete Module',
        updateModule: 'Update Module'
      }
    },
    lesson: {
      name: 'Lesson Manage',
      feature: {
        add: 'Add Lesson',
        delete: 'Delete Lesson',
        addTimetable: 'Add Timetable'
      }
    }
  },
  main: {
    functionList: 'Function List'
  },
  entity: {
    user: {
      name: 'Name',
      account: 'Account',
      authority: 'Authority',
      id: 'ID',
      password: 'password'
    },
    module: {
      name: 'Name',
      authority: 'Authority',
      route: 'Route',
      color: 'Color',
      icon: 'Icon',
      id: 'ID'
    },
    classroom: {
      id: 'ID',
      position: 'Position',
      tower: 'Tower',
      num: 'Number',
      type: 'Classroom'
    },
    lesson: {
      id: 'ID',
      name: 'Lesson Name'
    },
    appointment: {
      blank: '',
      mon: 'Monday',
      tue: 'Tuesday',
      wed: 'Wednesday',
      thu: 'Thursday',
      fri: 'Friday',
      sat: 'Saturday',
      sun: 'Sunday'
    },
    timetable: {
      lessonId: 'Lesson',
      activeId: 'Active',
      roomId: 'Classroom',
      day: 'Day',
      time: 'Time',
      startDate: 'Start Date',
      endDate: 'End Date'
    },
    active: {
      name: 'Name',
      roomId: 'Classroom',
      date: 'Date',
      time: 'Time'
    }
  },
  authority: {
    host: 'Host',
    manage: 'Manage',
    student: 'Student'
  },
  time: {
    one: '08:10-08:55',
    two: '09:00-09:45',
    three: '10:15-11:00',
    four: '11:05-11:50',
    five: '14:30-15:15',
    six: '15:20-16:05',
    seven: '16:25-17:10',
    eight: '17:15-18:00',
    nine: '19:10-19:55',
    ten: '20:00-20:45',
    eleven: '20:50-21:35'
  }
}
