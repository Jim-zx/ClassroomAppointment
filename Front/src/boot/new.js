import { boot } from 'quasar/wrappers'
const a = {
  props: {
    a: 3
  }
}
// "async" is optional;
// more info on params: https://v2.quasar.dev/quasar-cli/boot-files
export default boot(({ app }) => {
  // something to do
  app.config.globalProperties.$a = a
})
