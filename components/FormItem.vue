<template>
	<a-row :gutter="24">
		<a-col :sm="24" v-for="(item, index) in formItems.formItem" :key="index" :md="item.md">
			<a-form-item
		      :label="item.label"
		      :key="index"
			  :colon="formItems.mode==='edit' ? true : false"
		      :class="item.class"
		      :labelCol="{ span: item.labelCol ? item.labelCol : 6 }"
		      :wrapperCol="{ span: item.wrapperCol ? item.wrapperCol : 14 }"
			  v-decorator="[ item.id ]"     
		    >	
		    	<span v-if="formItems.mode==='edit'">
		        	<!-- Input 输入框 -->
			        <a-input 
			          v-if="item.type==='a-input'" 
			          v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]" 
			          :placeholder="item.placeholder"
			          :readOnly="item.readOnly" 
			          :maxLength="item.maxLength"
			          :allowClear="item.allowClear"
			          :prefix="item.prefix" 
			          :suffix="item.suffix"
			          :disabled="item.disabled?item.disabled:false"
			          @change="inputChange($event,item.change)" />

			        <!-- Textarea 文本域 -->
			        <a-textarea
			          v-if="item.type==='a-textarea'"
			          v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]" 
			          :placeholder="item.placeholder"
			          :autoSize="item.autosize"
			          :readOnly="item.readOnly"
			          :allowClear="item.allowClear"
			          :rows="item.rows" />

			        <!-- Search 搜索框 -->
			        <a-input-search 
			          v-if="item.type==='a-input-search'"
			          v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]" 
			          :placeholder="item.placeholder"
			          :enterButton="item.enterButton"
			          @search="(value,event) => inputSearch(value,event,item.search)">
			          <a-button v-if="item.slot" :slot="item.slot">
				        {{item.buttonName}}
				      </a-button>
			        </a-input-search>

			        <!-- Number 数字输入框 -->
			        <a-input-number
			          v-if="item.type==='a-input-number'"
			          v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]"
			          :min="item.min" 
			          :max="item.max" 
			          :step="item.step"
			          @change="(value) => inputNumberChange(value,item.change)" />

			        <!-- Radio 单选按钮 -->
			        <a-radio-group
			          v-if="item.type==='a-radio-group'"
			          v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]"
			          :disabled="item.disabled"
			          :name="item.name"
			          :size="item.size"
			          :buttonStyle="item.buttonStyle"
			          @change="(event) => radioChange(event,item.change)" >
					    <a-radio v-if="item.buttonStyle === ''" v-for="i in formItems.init[item.id]" :value="i.value" :key="i.index">
					    	{{i.text}}
						</a-radio>
						<a-radio-button v-if="item.buttonStyle !== ''" v-for="i in formItems.init[item.id]" :value="i.value" :key="i.index">
				          	{{i.text}}
				        </a-radio-button>
					</a-radio-group>

					<!-- Select 选择器 -->
					<a-select
					  v-if="item.type==='a-select'"
					  v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]"
					  :disabled="item.disabled"
					  :allowClear="item.allowClear"
					  :mode="item.mode"
					  :placeholder="item.placeholder"
					  :showSearch="item.showSearch"
					  :dropdownMatchSelectWidth = "item.dropdownMatchSelectWidth"
					  @change="(value,option) => selectChange(value,option,item.change)">
					    <a-select-option v-for="i in formItems.init[item.id]" :value="i.value" :key="i.index" :disabled="i.disabled ? i.disabled : false">
					      	{{i.text}}
					    </a-select-option>
				    </a-select>

				    <!-- Checkbox 多选框 -->
				    <a-checkbox-group
				      v-if="item.type==='a-checkbox-group'"
				      v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]"
				      :disabled="item.disabled"
				      :name="item.name"
				      @change="(checkedValue) => checkboxChange(checkedValue,item.change)"
				    >	
				    	<a-checkbox v-if="item.span===''" v-for="i in formItems.init[item.id]" :value="i.value" :disabled="i.disabled" :key="i.index">
					      	{{i.text}}
					    </a-checkbox>
				      	<a-row v-if="item.span!=''">
					      <a-col v-for="i in formItems.init[item.id]" :span="item.span" :key="i.index">
					        <a-checkbox :value="i.value" :disabled="i.disabled" :key="i.index">
					          	{{i.text}}
					        </a-checkbox>
					      </a-col>
					    </a-row>
				    </a-checkbox-group>

				    <!-- TimePicker 时间选择器 -->
				    <a-time-picker 
				      v-if="item.type==='a-time-picker'"
				      v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]"
				      :disabled="item.disabled"
				      :allowClear="item.allowClear"
				      :format="item.format"
				      :inputReadOnly="item.inputReadOnly"
				      :use12Hours="item.use12Hours"
				      @change="(time,timeString) => timePickerChange(time,timeString,item.change)" />

				    <!-- DatePicker 日期选择器 -->
				    <a-date-picker 
				      v-if="item.type==='a-date-picker'"
				      v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]"
				      :disabled="item.disabled"
				      :allowClear="item.allowClear"
				      :format="item.format"
				      :inputReadOnly="item.inputReadOnly"
				      :mode="item.mode"
				      :showTime="item.showTime"
				      :showToday="item.showToday"
				      :disabled-date="item.disabledDate"
				      @change="(date,dateString) => datePickerChange(date,dateString,item.change)" />

					<!-- DatePicker 日期选择器 (可选时间) -->
				    <a-date-picker 
				      v-if="item.type==='a-date-picker-addtime'"
				      v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]"
				      :disabled="item.disabled"
				      :allowClear="item.allowClear"
				      :format="item.format"
				      :inputReadOnly="item.inputReadOnly"
				      :showToday="item.showToday"
				      :disabled-date="item.disabledDate"
      				  :disabled-time="item.disabledDateTime"
				      showTime
				      @change="(date,dateString) => datePickerChange(date,dateString,item.change)" />

				    <!-- RangePicker 范围日期选择器 -->
				    <a-range-picker 
				      v-if="item.type==='a-range-picker'"
				      v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]"
				      :disabled="item.disabled"
				      :allowClear="item.allowClear"
				      :format="item.format"
				      :inputReadOnly="item.inputReadOnly"
				      :separator="item.separator"
				      :showTime="item.showTime"
				      @change="(dates,dateStrings) => rangePickerChange(dates,dateStrings,item.change)" />

			        <!-- span -->
			        <span
			          v-if="item.type==='span'" id="span1" >
			        	<a-popover v-if="!(dataSource[item.id] == '' || dataSource[item.id] == null)">
                            <template slot="content">
                                {{(dataSource[item.id] == '' || dataSource[item.id] == null) ? "\u3000" : dataSource[item.id]}}
                            </template>
                            {{(dataSource[item.id] == '' || dataSource[item.id] == null) ? "\u3000" : dataSource[item.id]}}
                        </a-popover>
                        <span v-else>
                            {{(dataSource[item.id] == '' || dataSource[item.id] == null) ? "\u3000" : dataSource[item.id]}}
                        </span>
			        </span>

			        <!-- Cascader 级联选择 -->
			        <a-cascader
			          v-if="item.type==='a-cascader'"
			          v-decorator="[ item.id, {initialValue:dataSource[item.id], rules: item.rules} ]"
			          :disabled="item.disabled"
			          :allowClear="item.allowClear"
			          :changeOnSelect="item.changeOnSelect"
			          :options="formItems.init[item.id]" 
			          :placeholder="item.placeholder" 
			          :expandTrigger="item.expandTrigger"
			          :displayRender="item.displayRender"
			          :fieldNames="item.fieldNames"
			          @change="(value, selectedOptions) => cascaderChange(value,selectedOptions,item.change)" />

			        <!-- slot 插槽 -->
			        <slot
			          v-if="item.type==='slot'"
			          :name="item.name"
			          :itme="item">
			        </slot>
		        </span>

		        <!-- 预览模式 -->
		        <span v-if="formItems.mode==='readOnly' && item.type != 'a-upload' " id="span2">
                    <a-popover v-if="!(dataSource[item.id] == '' || dataSource[item.id] == null)">
                        <template slot="content">
			                {{(dataSource[item.id] == '' || dataSource[item.id] == null) ? "\u3000" : dataSource[item.id]}}
                        </template>
			            {{(dataSource[item.id] == '' || dataSource[item.id] == null) ? "\u3000" : dataSource[item.id]}}
                    </a-popover>
                    <span v-else>
                        {{(dataSource[item.id] == '' || dataSource[item.id] == null) ? "\u3000" : dataSource[item.id]}}
                    </span>
                </span>
		        <!-- upload 上传文件 -->
		        <a-upload
		        	v-if="item.type==='a-upload'"
				    :action="item.action"
				    :file-list="formItems.init[item.id] ? formItems.init[item.id] : fileList "
				    :listType="item.listType"
					:multiple="item.multiple"
				    :disabled="item.disabled"
				    :remove="(file) => remove(file,item.remove)" 
				    :headers="item.headers"
				    :data="item.data"
				    :beforeUpload="item.beforeUpload"
				    @preview="handlePreview"
				    @change="(info) => uploadChange(info,item.change,item.id)" >
				    <div v-if="item.showButton == false ? false : true">
					    <a-button v-if="item.listType !=='picture-card'"> <a-icon type="upload" /> {{item.buttonText}} </a-button>
					    <div v-else>
					        <a-icon type="plus" />
					        <div class="ant-upload-text">
					          {{item.buttonText}}
					        </div>
					    </div>
					</div>
				</a-upload>
				
		    </a-form-item>

		</a-col>
		<a-modal :visible="previewVisible1" :footer="null" @cancel="handleCancel">
		   <img alt="example" style="width: 100%;height: auto;" :src="previewImage" />
		</a-modal>
		<a-modal :visible="previewVisible2" :footer="null" @cancel="handleCancel">
			<ShowPDF ref="previewPDF"></ShowPDF>
		</a-modal>
		<slot name="expand"></slot>
	</a-row>
</template>

<script type="text/javascript">
	import moment from 'moment';
	import ShowPDF from '@/pages/components/ShowPDF';
    export default {
        name: 'Form',
        components: {ShowPDF},
        props : {
        	formItems:{
        		type: Object,
        		default: {},
        	},
        	dataSource:{
        		type: Object,
        		default: {},
        	},
        },
        beforeCreate (){
			// this.form = this.$form.createForm(this);
		},
        data () {
            return {
            	fileList 		: [],
            	previewImage	: '',
            	previewVisible1	: false,
            	previewVisible2	: false,
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
        	inputChange(event,methodName){
        		let values ={methodName:methodName,event:event};
        		this.$emit('inputChange',values);
				this.$emit(methodName,values);
        	},

        	/**
        	 * search搜索框 search事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	inputSearch(value,event,methodName){
        		let values = {methodName:methodName,value:value,event:event};
        		this.$emit('inputSearch',values);
        		this.$emit(methodName,values);
        	},

        	/**
        	 * number数字输入框 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	inputNumberChange(value,methodName){
        		let values = {methodName:methodName,value:value};
        		this.$emit('inputNumberChange',values);
        		this.$emit(methodName,values);
        	},

        	/**
        	 * radio单选按钮 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	radioChange(event,methodName){
        		let values = {methodName:methodName,event:event};
        		this.$emit('radioChange',values);
        		this.$emit(methodName,values);
        	},

        	/**
        	 * select选择器 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	selectChange(value,option,methodName){
        		let values = {methodName:methodName,value:value,option:option};
        		this.$emit('selectChange',values);
        		this.$emit(methodName,values);
        	},

        	/**
        	 * checkbox多选框 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-18
        	 */
        	checkboxChange(checkedValue,methodName){
        		let values = {methodName:methodName,checkedValue:checkedValue};
        		this.$emit('checkboxChange',values);
        		this.$emit(methodName,values);
        	},

        	/**
        	 * time-picker时间选择器 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-17
        	 */
        	timePickerChange(time,timeString,methodName){
        		let values = {methodName:methodName,time:time,timeString:timeString};
        		this.$emit('timePickerChange',values);
        		this.$emit(methodName,values);
        	},

        	/**
        	 * date-picker日期选择器 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-18
        	 */
        	datePickerChange(date,dateString,methodName){
        		let values = {methodName:methodName,date:date,dateString:dateString};
        		this.$emit('datePickerChange',values);
        		this.$emit(methodName,values);
        	},

        	/**
        	 * range-picker范围日期选择器 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-18
        	 */
        	rangePickerChange(dates,dateStrings,methodName){
        		let values = {methodName:methodName,dates:dates,dateStrings:dateStrings};
        		this.$emit('rangePickerChange',values);
        		this.$emit(methodName,values);
        	},

        	/**
        	 * cascader级联选择 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-19
        	 */
        	cascaderChange(value,selectedOptions,methodName){
        		let values = {methodName:methodName,value:value,selectedOptions:selectedOptions};
        		this.$emit('cascaderChange',values);
        		this.$emit(methodName,values);
        	},

        	/**
        	 * upload文件上传 change事件
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-09-22
        	 */
        	uploadChange(info,methodName,id){
        		let fileList = [...info.fileList];
			      // 1. Limit the number of uploaded files
			      //    Only to show two recent uploaded files, and old ones will be replaced by the new
			      // fileList = fileList.slice(-2);

				  // 2. read from response and show file link
				  
			      fileList = fileList.map(file => {
			        if (file.response) {
			        	if (file.response.error && file.response.error.code === 403) {
			        		this.$notification['error']({
			                    message: '系统提示',
			                    description: '上传失败，不支持上传此格式的文件！',
			                    placement: 'topRight'
		                	});
		                	return false;
			        	}else if (file.response.error && file.response.error.code === 413) {
			        		this.$notification['error']({
			                    message: '系统提示',
			                    description: '上传失败，上传文件过大！',
			                    placement: 'topRight'
		                	});
		                	return false;
			        	}
			          // Component will show file.url as link
					  file.url = file.response.data.fullPath;
					  
					}
			        return file;
				  });
				  
			      let values = {fileList:fileList,methodName:methodName,id:id};

			    //   console.log(values);
			      if (values.fileList.length == 0) {
			      	if(methodName != 'uploadChange'){
			      		this.$emit('uploadChange',values);
			      		this.$emit(methodName,values);
			      	}else{
			      		this.$emit('uploadChange',values);
			      	}
			      }else if (values.fileList[values.fileList.length - 1].status !== 'uploading') {
			      	if(methodName != 'uploadChange'){
			      		this.$emit('uploadChange',values);
			      		this.$emit(methodName,values);
			      	}else{
			      		this.$emit('uploadChange',values);
			      	}
			      }
				  this.fileList = fileList;
				  this.formItems.init[id] = fileList;
				  
        	},

        	/**
        	 * 清除上传文件列表
        	 *
        	 * @Author   lu_shuai
        	 * @DateTime 2020-11-12
        	 */
        	clearList(){
        		this.fileList = [];
        	},

        	handlePreview(file) {
		      if (!file.url && !file.preview) {
		        file.preview =  getBase64(file.originFileObj);
			  }
		      let url = file.url;
		      let index = url.lastIndexOf(".");
		      let ext = url.substr(index+1).toLocaleLowerCase();
		      const imglist = ['png', 'jpg', 'jpeg', 'bmp','tif','pdf'];
		      if (imglist.indexOf(ext) == -1) {
		      	this.$notification['error']({
                    message: '系统提示',
                    description: '此文件不支持预览！',
                    placement: 'topRight'
                });
                return false;
		      }

		      if(ext === 'pdf'){
		      	this.previewVisible2 = true;
		      	this.$nextTick(()=>{
				    this.$refs.previewPDF.setPdfUrl(file.url);
				});
		      } else {
		      	this.previewImage = file.url || file.preview;
		      	this.previewVisible1 = true;
		      }
		      
		    },

		    handleCancel() {
		      this.previewVisible1 = false;
		      this.previewVisible2 = false;
		    },

        	// click(){
        	// 	this.form.validateFields((err,values)=>{
        	// 		if (err) {
        	// 			return false;
        	// 		} else {
        	// 			let _this = this;
        	// 			let value = {this:_this,values:values};
        	// 			// console.log(value);
        	// 			this.$emit('submit',value);
        	// 		}
        	// 	});
        	// },
        	// 
        	
        	remove(file,methodName){
        		let value = {
        			'methodName'	: methodName,
        			'file'			: file,
        		};
        		this.$emit(methodName,value);
        	},
        }
    }


function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

</script>

<style scoped>
  .hide{
    display:none
  }

#span1,#span2{
    word-break:normal; 
    width:auto; 
    display:block; 
    white-space:nowrap;
    text-overflow:ellipsis;
    word-wrap : break-word ;
    overflow: hidden ;
} 
</style>