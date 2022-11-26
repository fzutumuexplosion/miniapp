import { createStore } from 'vuex'
import app from './modules/app'
// 导入getters
import getters from './getters'

export default createStore({
  modules: {
    app
  },
  getters
})
