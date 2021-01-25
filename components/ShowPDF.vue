<template>
	<div class="" >
    <iframe :src="pdfUrl" class="pdfArea" :style="{height : height}"></iframe>
  </div>
</template>

<script>
	export default {
    name: 'showPDF',
    props: {
      
    },
		data () {
      return {
       	pdfUrl: '',//pdf文件的路径
      }	
    },
    activated () {

    },
    computed:{
      height: function(){
        return window.screen.availHeight * 1 + "px";
      }
    },
    methods: {
      setPdfUrl(val){
		    console.log(val);
        if (val == '') {
          this.pdfUrl = '';
        } else {
          val = val.replace(".pdf",".pdf?PREVIEW_TYPE=1");
          val = val.replace(".docx",".pdf?PREVIEW_TYPE=1");
          this.pdfUrl = val + '?r=' + new Date();
        }
      },
    }
	}
</script>
<style scoped>
.pdfArea {
  width: 100%;
  /*height: 600px;*/
}
</style>