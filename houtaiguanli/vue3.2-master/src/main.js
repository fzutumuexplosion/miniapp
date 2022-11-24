import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 导入scss文件
import '@/styles/index.scss'
// 导入自己的图标
import SvgIcon from '@/icons/index'
// 导入element-plus的样式，例如显示密码错误的样式
import 'element-plus/dist/index.css'
// 导入路由守卫的
import '@/router/permission'
import * as ELIcons from '@element-plus/icons-vue'
import i18n from '@/i18n'
import filters from '@/util/filters'

const app = createApp(App)
for (const iconName in ELIcons) {
  app.component(iconName, ELIcons[iconName])
}

filters(app)
SvgIcon(app)
app.use(store).use(router).use(i18n).mount('#app')
