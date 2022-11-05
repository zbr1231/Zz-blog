// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import './assets/css/style.less'
import store from './store'
import plugins from './plugins'//plugins
import MavonEditor from 'mavon-editor'
import GoEasy from 'goeasy'
import "mavon-editor/dist/css/index.css";

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(MavonEditor)
Vue.use(plugins)

Vue.prototype.goeasy = GoEasy.getInstance({
  host:"hangzhou.goeasy.io",  //若是新加坡区域：singapore.goeasy.io
  appkey:"BC-68d9a72970754dbab8755352036b0e55",
  modules:['pubsub']//根据需要，传入‘pubsub’或'im’，或数组方式同时传入
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store
})
