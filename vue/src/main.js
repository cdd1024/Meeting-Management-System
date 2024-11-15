import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { createStore } from 'vuex';

import '@/assets/css/global.css'
const store = createStore({
    state: {
        // your state properties
    },
    mutations: {
        saveEditorUrl(state, url) {
            state.editorUrl = url;
        },
        // your mutations
    },
    actions: {
        // your actions
    },
    getters: {
        // your getters
    }
});
const app = createApp(App)

app.use(router)
app.use(ElementPlus, {
    locale: zhCn,
})
app.mount('#app')

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}