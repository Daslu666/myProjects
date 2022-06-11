# myProjects
我的项目
第一次提交



# Form 表单组件使用说明

##### 一、如何使用

（1）在页面中引入Form组件，如下：

```vue
<script>
	import Form  from '../components/Form';
	export default {
		components: {Form},  
	};
</script>
```

（2）在template标签中使用<Form></Form>标签来使用组件，如下：

```vue
<template>
  <div>
    <a-card>
       <Form ref="Form" :formItems="formItems" :dataSource="dataSource">
       </Form>
    </a-card>
  </div>
</template>
```

其中 "ref" 用于指定此组件，方便于在此页面中调用Form组件的方法，使用方法为 this.$refs.Form.xxx();（xxx为Form组件的方法名）； formItems 是传给Form组件的一个对象，用于动态渲染Form组件的表单内容；dataSource是传给Form组件的一个数据源对象。

##### 校验rules

1.可选type：在rules中添加{type: 'xxx', message: 'xxx',}   如下。

- ```
  Indicates the `type` of validator to use. Recognised type values are:
  
  - `string`: Must be of type `string`. `This is the default type.`
  - `number`: Must be of type `number`.
  - `boolean`: Must be of type `boolean`.
  - `method`: Must be of type `function`.
  - `regexp`: Must be an instance of `RegExp` or a string that does not generate an exception when creating a new `RegExp`.
  - `integer`: Must be of type `number` and an integer.
  - `float`: Must be of type `number` and a floating point number.
  - `array`: Must be an array as determined by `Array.isArray`.
  - `object`: Must be of type `object` and not `Array.isArray`.
  - `enum`: Value must exist in the `enum`.
  - `date`: Value must be valid as determined by `Date`
  - `url`: Must be of type `url`.
  - `hex`: Must be of type `hex`.
  - `email`: Must be of type `email`.
  - `any`: Can be any type.
  ```

  

2.validator 自定义校验 function(rule, value, callback)：

在rules中添加如下代码：
```javascript
{
    validator: function(rule, value, callback) {
        if (value != '123') {
            callback(new Error('请输入123！'));
        } else {
            callback();
        }
    }
}
```

3.是否为必填：

在rules中添加 { required: true, message: '请输入' }。

（3）维护页面的formItems内容的js文件，如下是我创建的 formData.js文件内容：

```javascript
const test = {
	mode: 'readOnly',
	// readOnly 只读 | edit 编辑
	formItem: [{
		type: 'a-input',
		//type为'a-input'，在页面中会生成一个input输入框
		label: '',
		//标题
		id: '',
		//表单的id，一般使用数据的字段名称，如'name','age'
		class: '',
		//表单中formItem的class属性，用于设置样式，目前可使用的为'hide'（隐藏此表单对象）
		labelCol: 6,
		//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
		wrapperCol: 10,
		//wrapperCol为表单（例如输入框）占用的栅格数
		rules: [{
			required: true,
			message: '请输入'
		}],
		//表单的校验规则，required：true|false,true为必填项，false为非必填
		placeholder: '请输入',
		//默认内容
		readOnly: false,
		//是否为只读格式，true为只读，false为非只读。
		//maxLength		: 100,	//最大长度（可选属性，在使用栅格时无效，一般不用）
		allowClear: false,
		//是否出现清除按钮
		prefix: '',
		//带有前缀图标的 input 	string|slot	(11月10新增)
		suffix: '',
		//带有后缀图标的 input 	string|slot	(11月10新增)
		change: '',
		//change事件的方法名，在页面中定义方法
		md: 24,
		//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
	},
	{
		type: 'a-input',
		label: '用户账号111',
		id: 'USR_USERNAME',
		class: '',
		labelCol: 10,
		wrapperCol: 14,
		// initialValue	: '',
		rules: [{
			validator: function(rule, value, callback) {
				if (value != '123') {
					callback(new Error('请输入123！'));
				} else {
					callback();
				}
			}
		},
		{
			type: 'email',
			message: '请输入正确的邮箱格式!',
		},
		{
			required: true,
			message: '请输入'
		}],
		placeholder: '请输入',
		readOnly: false,
		maxLength: 100,
		allowClear: false,
		change: '',
		md: 12,
	},
	{
		type: 'a-cascader',
		label: '所属部门',
		id: 'GRP_NAME',
		class: '',
		labelCol: 10,
		wrapperCol: 14,
		rules: [{
			required: true,
			message: '请选择'
		}],
		placeholder: '请选择',
		allowClear: false,
		disabled: false,
		changeOnSelect: false,
		expandTrigger: 'hover',
		displayRender: function({
			labels
		}) {
			return labels[labels.length - 1];
		},
		change: '',
		md: 12,
	},
	],
	init: { //初始化表单数据，可在js配置，也可在页面中动态设置
		key: '...' //key为表单所对应的id like GRP_NAME:[]
	},
};
export default {
	test,
};
```

在你的页面中引入此js文件，如下：

```vue
<script>
	import Form  from '../components/Form';
    	import formData from '@/mock/common/formData';
	export default {
      		components: {Form},  
    	};
</script>
```

在data中定义一个formItems为formData中定义的test对象，如下：

```vue
<script>
	import Form  from '../components/Form';
    	import formData from '@/mock/common/formData';
	export default {
      		components: {Form},  
      		data () {
        		return {
          			formItems: formData.test,
      			}
  	  	},
    	};
</script>
```

在data中定义一个form为null，在methods中定义一个方法用于获取Form组件的form对象，并在activated()函数中使用此方法，如下：

```vue
<script>
	import Form  from '../components/Form';
    	import formData from '@/mock/common/formData';
	export default {
      		components: {Form},
      		data () {
        		return {
              			formItems: [],
            			form: null,
            		}
        	},
        	activated(){
            		this.createForm();
        	},
        	methods: {
		    /**
		     * 获取Form组件中的form对象
		     */
		    createForm(){
			this.form = this.$refs.Form.form;
		    },
        	}
    	};
</script>
```

在data中定义一个空对象"dataSource"(名称任意定义)，用于渲染表单数据，如下：

```vue
<script>
	import Form  from '../components/Form';
    import formData from '@/mock/common/formData';
	export default {
      components: {Form},
      data () {
        return {
              	formItems: [],
            	form: null,
            	dataSource: {
                    'name':'zhangsan'
                },
            }
        },
        activated(){
            this.createForm();
        },
        methods: {
            /**
             * 获取Form组件中的form对象
             */
            createForm(){
                this.form = this.$refs.Form.form;
            },
            /**
             * 设置表单数据dataSource（类似于table）
             */
            setDataSource(){
                this.dataSource = （yourData，数据）; 
            },
            
            /**
             * 初始化表单
             */
            setInit(){
              	this.formItems.init.（id，对应表单的id） = （yourData，数据）;
            },
        }
    };
</script>
```

##### 二、在js中维护的form表单对象

###### （1）<a-input> input输入框

```js
		{
      		type			: 'a-input',//type为'a-input'，在页面中会生成一个input输入框
      		label			: '',//标题
      		id				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: 'hide',//表单中formItem的class属性，用于设置样式，目前可使用的为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: true, message: '请输入' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		placeholder		: '请输入',//默认内容
      		readOnly		: false,//是否为只读格式，true为只读，false为非只读。
      		maxLength		: 100,	//最大长度（可选属性，在使用栅格时无效，一般不用）
      		allowClear		: false,//是否出现清除按钮
            prefix			: '',//带有前缀图标的 input 	string|slot	(11月10新增)
            suffix			: '',//带有后缀图标的 input 	string|slot	(11月10新增)
      		change			: '',//change事件的方法名，在页面中定义方法
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （2）<a-textarea>textarea文本域

```javascript
		{
      		type			: 'a-textarea',//type为'a-textarea'，在页面中会生成一个textarea文本域
      		label 			: '',//标题
      		id  			: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: true, message: '请输入' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		placeholder		: '请输入',//默认内容
      		autosize		: false,//是否根据内容自动设置大小，true为是，false为否
      		readOnly		: false,//是否为只读格式，true为只读，false为非只读。
      		allowClear		: false,//是否出现清除按钮
      		rows			: 4,//textarea的行数，在设置了autosize后无效
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （3）<a-input-search>search搜索框

```javascript
		{
      		type			: 'a-input-search',//type为'a-input-search'，在页面中会生成一个search搜索框
      		label 			: '',//标题
      		id  			: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: false, message: '请输入' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		placeholder		: '搜索',//默认内容
      		enterButton		: '',//是否有确认按钮，可设为按钮文字。
      		// slot			: 'enterButton',//在使用自定义的按钮时使用该属性
      		// buttonName	: '',//在使用自定义的按钮时使用该属性，按钮的内容
      		loading			: false,//按钮的loading格式
      		search 			: '',//search事件的方法名，在页面中定义方法
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （4）<a-input-number>number数字输入框

```javascript
		{
      		type 			: 'a-input-number',//type为'a-input-number'，在页面中会生成一个number数字输入框
      		label 			: '',//标题
      		id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: false, message: '请输入' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		min				: 0,//最小值
      		max 			: 10,//最大值
      		step 			: 0.1,//每点击一次按钮增加或者减少的值，可以设置为小数
      		change			: '',//change事件的方法名，在页面中定义方法
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （5）<a-radio-group>radio单选按钮

```javascript
		{
      		type 			: 'a-radio-group',//type为'a-radio-group'，在页面中会生成radio单选按钮
      		label 			: '',//标题
      		id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: false, message: '请选择' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		disabled		: false,//是否为禁用状态，true是 | false否
      		name 			: '',//RadioGroup 下所有 input[type="radio"] 的 name 属性
      		size			: 'default', //大小，只对按钮样式生效	large | default | small
      		buttonStyle 	: 'outline', //RadioButton 的风格样式，目前有描边和填色两种风格	outline | solid,如果不想使用按钮格式请设置为空''
      		change 			: '',//change事件的方法名，在页面中定义方法
      		//options 		: [	//按钮选项，内容格式如下
      		//	{
      		//		value 	: '',//选项值
      		//		index	: 1,//索引
      		//		text 	: '',//显示内容
      		//	},
      		//],
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （6）<a-select>select选择器

```javascript
		{
      		type 			: 'a-select',//type为'a-select'，在页面中会生成select选择器
      		label 			: '',//标题
      		id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: true, message: '请选择' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		disabled		: false,//是否为禁用状态，true是 | false否
      		allowClear		: true,//是否出现清除按钮
      		mode			: 'default', //设置 Select 的模式为多选或标签	'default' | 'multiple' | 'tags' | 'combobox'
      		placeholder		: '请选择',//默认内容
      		showSearch		: false,//使单选模式可搜索
            dropdownMatchSelectWidth : true,//下拉菜单和选择器同宽 默认为true (11月11日新增)
      		change 			: '',//change事件的方法名，在页面中定义方法
      		//options 		: [ //选项内容如下
      		//	{
      		//		value 	: '',//选项值
      		//		index	: 1,//索引
      		//		text 	: '',//显示内容
      		//	},
      		//],
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （7）<a-checkbox-group>checkbox多选框

```javascript
		{
      		type			: 'a-checkbox-group',//type为'a-checkbox-group'，在页面中会生成checkbox多选框
      		label 			: '',//标题
      		id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: false, message: '请选择' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		disabled		: false,//是否为禁用状态，true是 | false否
      		name 			: '',//CheckboxGroup 下所有 input[type="checkbox"] 的 name 属性
      		change 			: '',//change事件的方法名，在页面中定义方法
      		span			: '',//选项的栅格布局，为''时不会进行栅格布局 一般使用 6|8|12
      		//options 		: [//选项内容如下
      		//	{
      		//		index	: 1,//索引
      		//		value 	: '',//选项值
      		//		disabled: false,//选项是否禁用
      		//		text 	: '',//显示内容
      		//	},
      		//],
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （8）<a-time-picker>timepicker时间选择器

```javascript
		{
      		type			: 'a-time-picker',//type为'a-time-picker'，在页面中会生成timepicker时间选择器
      		label 			: '',//标题
      		id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: true, message: '请选择' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		disabled		: false,//是否为禁用状态，true是 | false否
      		allowClear		: true,//是否出现清除按钮
      		format			: '',//展示的时间格式
      		inputReadOnly	: false,//设置输入框为只读（避免在移动设备上打开虚拟键盘）
      		use12Hours		: true,//使用 12 小时制，为 true 时 format 默认为 h:mm:ss a
      		change 			: '',//change事件的方法名，在页面中定义方法
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （9）<a-date-picker>datepicker日期选择器

```javascript
		{
      		type 			: 'a-date-picker',//type为'a-date-picker'，在页面中会生成datepicker日期选择器
      		label 			: '',//标题
      		id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: true, message: '请选择' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		disabled 		: false,//是否为禁用状态，true是 | false否
      		allowClear		: true,//是否出现清除按钮
      		format			: '',//展示的时间格式
      		inputReadOnly	: false,//设置输入框为只读（避免在移动设备上打开虚拟键盘）
      		mode 			: 'date', //日期面板的状态（设置后无法选择年份/月份）	time|date|month|year|decade
            disabledDate    : (current)=>{    //不可选择的日期，2020-12-01新增
                        return current > moment().endOf('day');
            },
      		showTime		: false,//不可选择时间
      		showToday		: true,//是否展示“今天”按钮
      		change 			: '',//change事件的方法名，在页面中定义方法
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （9.1）<a-date-picker-addtime>datepicker日期选择器(可以选择时间) 

（2020-11-11 新增）

```js
{
      		type 			: 'a-date-picker-addtime',//type为'a-date-picker-addtime'，在页面中会生成datepicker日期选择器(可以选择时间)
      		label 			: '',//标题
      		id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: true, message: '请选择' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		disabled 		: false,//是否为禁用状态，true是 | false否
      		allowClear		: true,//是否出现清除按钮
      		format			: '',//展示的时间格式
      		inputReadOnly	: false,//设置输入框为只读（避免在移动设备上打开虚拟键盘）
      		showToday		: true,//是否展示“今天”按钮
            disabledDate    : (current)=>{    //不可选择的日期
                        return current > moment().endOf('day');
            },
            disabledDateTime: (date)=>{	//不可选择的时间 
                        var date = new Date();//获取当前时间
                        let h = date.getHours();
                        let m = date.getMinutes();
                        let s = date.getSeconds();
                        return{
                              disabledHours: () => range(h+1, 24),	//range方法如下
                              disabledMinutes: () => range(m+1, 60),
                        }
            },
      		change 			: '',//change事件的方法名，在页面中定义方法
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

```js
function range(start, end) {
  const result = [];
  for (let i = start; i < end; i++) {
    result.push(i);
  }
  return result;
}
```



###### （10）<a-range-picker>rangepicker日期选择器

```javascript
		{
      		type 			: 'a-range-picker',//type为'a-range-picker'，在页面中会生成rangepicker日期选择器
      		label 			: '',//标题
      		id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		rules			: [{ required: true, message: '请选择' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
      		disabled 		: false,//是否为禁用状态，true是 | false否
      		allowClear		: true,//是否出现清除按钮
      		format			: '',//展示的时间格式
      		inputReadOnly	: false,//设置输入框为只读（避免在移动设备上打开虚拟键盘）
      		separator		: '~',//设置分隔符
      		showTime		: false,//增加时间选择功能
			change 			: '',//change事件的方法名，在页面中定义方法
			md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （11）<a-cascader>cascader级联选择

```javascript
		{
	    	type			: 'a-cascader',//type为'a-cascader'，在页面中会生成cascader级联选择
	    	label			: '',//标题
	    	id				: '',//表单的id，一般使用数据的字段名称，如'name','age'
	    	class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
	    	labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
	    	wrapperCol		: 14,//wrapperCol为表单（例如输入框）占用的栅格数
	    	rules			: [{ required: true, message: '请选择' }],//表单的校验规则，required：true|false,true为必填项，false为非必填
	    	placeholder		: '请选择',//默认内容
	    	allowClear		: false,//是否出现清除按钮
	    	disabled 		: false,//是否为禁用状态，true是 | false否
	    	changeOnSelect  : false,//当此项为 true 时，点选每级菜单选项值都会发生变化,选择即改变
	    	//options			: 选项内容如下,//可选项数据源
                
	    	expandTrigger	: 'hover', //次级菜单的展开方式，可选 'click' 和 'hover'	
	    	displayRender	: function({labels}){//选择后展示的渲染函数,可使用 slot="displayRender" 和 slot-scope="{labels, selectedOptions}
	    		return labels[labels.length - 1];
	    	},
            fieldNames		: {},//自定义 options 中 label name children 的字段,例如：{ label: 'name', value: 'code', children: 'items' }
	    	change			: '',//change事件的方法名，在页面中定义方法
	    	md    			: 12,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
	    },
```

选项内容：

```javascript
[        
    	{
            value: 'zhejiang',
         	label: 'Zhejiang',
         	children: [
                            {
                                value: 'hangzhou',
                                label: 'Hangzhou',
                                children: [
                                    {
                                        value: 'xihu',
                                        label: 'West Lake',
                                    },     
                                ],
                            },
            ],
        },        
        {          
            value: 'jiangsu',          
            label: 'Jiangsu',          
            children: [
                			{              
                                value: 'nanjing',              
                                label: 'Nanjing',              
                                children: [                
                                    {                  
                                        value: 'zhonghuamen',                  
                                        label: 'Zhong Hua Men',                
                                    },              
                                ],            
                            },
            ],        
        },      
    ],
```



###### （12）<span>span只读

```javascript
		{
      		type 			: 'span',//type为'span'，在页面中会生成span
      		label 			: '',//标题
      		id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
      		class			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
      		labelCol		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
      		wrapperCol		: 10,//wrapperCol为表单（例如输入框）占用的栅格数
      		md    			: 24,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
      	},
```

###### （13）<slot>slot 插槽

```javascript
		{
	      	type			: 'slot',//type为'slot'，在页面中会生成slot插槽
	      	label 			: '',//标题
	      	id 				: '',//表单的id，一般使用数据的字段名称，如'name','age'
	      	class 			: '',//表单中formItem的class属性，用于设置样式，目前可使用的										   为'hide'（隐藏此表单对象）
	      	labelCol 		: 6,//栅格形式的布局，一行为24，labelCol为输入框标题占用的栅格数
	      	wrapperCol		: 14,//wrapperCol为表单（例如输入框）占用的栅格数
	      	name 			: 'name',//插槽的名称
	      	md 				: 12,//栅格布局，一般为 8|12|24 （8：一行三个表单对象，12一行两个，24一行一个）注意！在设置隐藏此表单对象时需要删除此属性，否则该表单还会占用页面空间
	    },
```

###### （14）<a-upload>upload 上传文件

```js
			{
	      		type			: 'a-upload',
	      		label			: '上传文件',
	      		id				: 'uploadFile',
	      		class			: '',
	      		labelCol		: 6,
	      		wrapperCol		: 14,
	      		action			: '',//上传文件接口地址
                headers			: {},//请求头信息（2020-11-12新增）
                data			: {},//上传所需参数或返回上传参数的方法（2020-12-24新增）
                showButton		: true, //可选属性：是否显示上传按钮，默认为true（2020-12-08新增）
	      		listType 		: 'picture',//上传列表的内建样式，支持三种基本样式 text, picture 和 picture-card
	      		disabled 		: false,
	      		remove 			: 'remove',//remove事件方法名
	      		buttonText		: '上传文件',//按钮显示文字
	      		change			: 'uploadChange',//change事件方法名
	      		md    			: 24,
	      	},
```

（2020-11-18新增）支持已上传文件回显，回显数据格式如下：

```
{
          uid: '1',
          name: 'xxx.png',
          status: 'done',
          response: 'Server Error 500', // custom error message to show
          url: 'http://www.baidu.com/xxx.png',
},
```

回显方式，在formItem对象的init属性中添加初始化值：

```
init 					: {	//初始化表单数据，可在js配置，也可在页面中动态设置
	uploadFile			: [],//uploadFile为组件的id值
},
```

在接口中返回如上述回显格式的数据后，使用init属性设置上传列表的值：

```
let fileList = （你的数据，例如：[{
          uid: '1',
          name: 'Chrysanthemum.jpg',
          status: 'done',
          url: 'http://localhost:2022/shared/20201118/2454833985fb4dbe6874a69087845203.jpg',
        },]; ）;

this.formItem.init.uploadFile = fileList;
```



##### 三、slot插槽使用方法

slot插槽可以在Form组件中自由定义各种的form表单，以满足复杂需求。

（1）在js文件中维护一个slot对象，如下：

```javascript
const userDetails = function userDetails(data){	//data为传入的数据
		return [
			{
	      		type			: 'slot',
	      		label 			: '插槽',
	      		id 				: 'slot',
	      		class 			: '',
	      		labelCol 		: 6,
	      		wrapperCol		: 14,
	      		name 			: 'name',
	      		md 				: 12,
	      	},
	   ];
}
export default {
    userDetails,
};
```

（2）使用slot在页面中Form组件插入一个input输入框，如下：

```vue
<template>
  <div>
    <a-card>
       <Form ref="Form" :formItems="formItems">
       		<template v-slot:nameauto> <!-- 插槽名称为（定义的name+auto），注意：直接使用定义的name无效（bug修改：2020-11-18） -->
       			<a-input
       			  v-decorator="[ 'name', {initialValue:'张三', rules: [{ required: true, message: '请选择' }]} ]" 
       			  placeholder="请输入姓名">
       			</a-input>
       		</template>
       </Form>
    </a-card>
  </div>
</template>
```

使用Form组件时：在<Form></Form>中间插入代码，其中v-slot:xxx（xxx对应js中slot对象的'name'属性+‘auto’）。

使用FormItem组件时：在<FormItem></FormItem>中间插入代码，其中v-slot:xxx（xxx对应js中slot对象的'name'属性）。



##### 四、底部操作栏（底部按钮插槽）

（1）在页面的data中定义一个参数showSubmit（参数名，任意定义），如下

```vue
<script>
	import Form  from '../components/Form';
    import formData from '@/mock/common/formData';
	export default {
      components: {Form},
      data () {
        return {
              	formItems: [],
            	form: null,
            	showSubmit: true, //true显示底部操作栏,false不显示
            }
        },
        activated(){
            this.createForm();
        },
        methods: {
            /**
             * 获取Form组件中的form对象
             */
            createForm(){
                let form = this.$refs.Form.createForm();
                this.form = form;
            },
        }
    };
</script>
```

（2）在Form中使用插槽，可使用左边和右边按钮，如下：

```vue
<template>
  <div>
    <a-card>
       <Form ref="Form" :formItems="formItems" :showSubmit="showSubmit">
       	   <!-- 右边按钮 -->
           <template v-slot:button>
       			<a-button @click="">返回</a-button>
	            <a-button type="primary" @click="save">保存</a-button>
       		</template>
			<!-- 左边按钮 -->
       		<template v-slot:buttonLeft>
       			<a-button @click="">返回</a-button>
	            <a-button type="primary" @click="save">保存</a-button>
       		</template>
       </Form>
    </a-card>
  </div>
</template>
```

