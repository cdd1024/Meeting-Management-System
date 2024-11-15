<template>
  <div style="border: 1px solid #ccc; width: 100%">
    <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :defaultConfig="toolbarConfig" mode="default"/>
    <Editor style="height: 400px; overflow-y: hidden" v-model="richText" :defaultConfig="editorConfig" mode="default"
            @onCreated="handleCreated" @customPaste="customPaste"/>
  </div>
</template>

<script setup>
import '@wangeditor/editor/dist/css/style.css';
import {Editor, Toolbar} from '@wangeditor/editor-for-vue';
import {onMounted, onBeforeUnmount, shallowRef, ref, watch, nextTick} from 'vue';

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
})
const emits = defineEmits(['update:modelValue'])
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef();
const richText = ref('');
// 内容 HTML
const toolbarConfig = {};
const editorConfig = ref({placeholder: '请输入内容...', MENU_CONF: {}});
watch(richText, () => {
  emits('update:modelValue', richText.value);
});
// 自定义图片上传
editorConfig.value.MENU_CONF['uploadImage'] = {
  base64LimitSize: 1024 * 1024 * 10 // 10mb
};
// 富文本编辑器生成后触发
const handleCreated = editor => {
  editorRef.value = editor; // 记录 editor 实例，重要！
};
// 监听富文本编辑器粘贴行为
const customPaste = (editor, event, callback) => {
  // 获取粘贴的纯文本
  const text = event.clipboardData.getData('text/plain');
  if (text) {
    editor.insertText(text);
    event.preventDefault();
    callback(false);
  }
};
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
});
onMounted(() => {
  nextTick(() => {
      richText.value = props.modelValue;
  })
});
</script>
<link href="https://unpkg.com/@wangeditor/editor@latest/dist/css/style.css" rel="stylesheet">
<style>
#editor—wrapper {
  border: 1px solid #ccc;
  z-index: 100; /* 按需定义 */
}
#toolbar-container {
  border-bottom: 1px solid #ccc;
}
#editor-container {
  height: 500px;
}
</style>