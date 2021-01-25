<template>
	<div>
		<a-form :form="form">
			<FormItem 
				ref="FormItem" 
				:formItems="formItems" 
				:dataSource="dataSource" 
				@inputChange="inputChange" 
				@inputSearch="inputSearch" 
				@inputNumberChange="inputNumberChange"
				@radioChange="radioChange"
				@selectChange="selectChange"
				@checkboxChange="checkboxChange"
				@timePickerChange="timePickerChange"
				@datePickerChange="datePickerChange"
				@rangePickerChange="rangePickerChange"
				@cascaderChange="cascaderChange"
                @uploadChange="uploadChange"
                @removeFile="removeFile">
                <slot
                    v-for="(item, index) in formItems.formItem"
                    v-if="item.type==='slot'"
                    :name="item.name+'auto'"
                    :slot="item.name">
                </slot>
			</FormItem>

                <slot></slot>
			

	        <a-form-item v-if="showSubmit">
	          <footer-tool-bar>
	          	<template v-slot:extra>
	          		<slot name="buttonLeft"></slot>
	          	</template>
	          	<template v-slot:default>
	          		<slot name="button"></slot>
	          	</template>
	          </footer-tool-bar>
	        </a-form-item>
      </a-form>
	</div>
</template>

<script type="text/javascript">
	import moment from 'moment';
	import FooterToolBar from '@/components/tool/FooterToolBar';
	import FormItem from '@components/FormItem';
    export default {
        name: 'Form',
        components: {FooterToolBar,FormItem},
        props : {
        	formItems:{
        		type: Object,
        		default: function(){ return {}; },
        	},
        	showSubmit:{
        		type: Boolean,
        		default: false,
        	},
        	dataSource:{
        		type: Object,
        		default: function(){ return {}; },
        	},
        },
        beforeCreate (){
			this.form = this.$form.createForm(this);
		},
        data () {
            return {
            }
        },
        activated(){
        	
        },
        methods: {
        	moment,
        	/**
        	 * input输入框 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	inputChange(value){
        		this.$emit(value.methodName,value);
        	},

        	/**
        	 * search搜索框 search事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	inputSearch(value){
        		this.$emit(value.methodName,value);
        	},

        	/**
        	 * number数字输入框 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	inputNumberChange(value){
        		this.$emit(value.methodName,value);
        	},

        	/**
        	 * radio单选按钮 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	radioChange(value){
        		this.$emit(value.methodName,value);
        	},

        	/**
        	 * select选择器 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	selectChange(value){
        		this.$emit(value.methodName,value);
        	},

        	/**
        	 * checkbox多选框 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-18
        	 */
        	checkboxChange(value){
        		this.$emit(value.methodName,value);
        	},

        	/**
        	 * time-picker时间选择器 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	timePickerChange(value){
        		this.$emit(value.methodName,value);
        	},


        	/**
        	 * date-picker日期选择器 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-18
        	 */
        	datePickerChange(value){
        		this.$emit(value.methodName,value);
        	},

        	/**
        	 * range-picker范围日期选择器 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-18
        	 */
        	rangePickerChange(value){
        		this.$emit(value.methodName,value);
        	},

        	/**
        	 * cascader级联选择 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-19
        	 */
        	cascaderChange(value){
        		this.$emit(value.methodName,value);
        	},

            /**
             * upload文件上传 change事件
             *
             * @Author   lu_shuai
             * @DateTime 2020-11-18
             */
            uploadChange(value){
                this.$emit(value.methodName,value);
            },

            /**
             * 删除文件
             *
             * @Author   lu_shuai
             * @DateTime 2020-11-20
             */
            removeFile(value){
                this.$emit(value.methodName,value);
            },

        }
    }


</script>

<style scoped>
  .hide{
    display:none
  }
</style>