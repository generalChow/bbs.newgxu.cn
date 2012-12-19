var uploadArea = null;
var uploadAreaDialog;
function getUploadAreaDialog(dialog, w, h) {
	uploadArea = new newgxu.UploadArea(dialog, 5);
	uploadAreaDialog = new newgxu.HtmlDialog(dialog, 360, 240);
}
	

var newgxu = window.newgxu || new Object();
newgxu.UploadArea = new Class({
	initialize: function(el, maxSize){
		this.el = $(el);
		
		this.iframeDiv = new Element('div');
		this.iframeDiv.innerHTML = '<iframe name="upload_hidden_frame" id="upload_hidden_frame" style="display:none"></iframe>';
		this.iframeDiv.injectInside(this.el);
		
		this.tmp = new Element('div');
		this.tmp.innerHTML = '<form target="upload_hidden_frame" method="post" enctype="multipart/form-data" action="/upload_servlet" id="upload_form" />';
		
		this.tmp.injectInside(this.el);
		this.form = $("upload_form");
		
		$(this.form).onsubmit = function() {
			return this.onsubmit();
		}.bind(this);
		
		this.tmpDiv=new Element('div');
		this.tmpDiv.id='upload_div';
		this.tmpDiv.injectInside(this.form);
		this.tmpDiv=$("upload_div");
		
		this.addButton = new Element('input');
		this.addButton.id = 'upload_addButton';
		this.addButton.type = 'button';
		this.addButton.value = '继续添加';
		this.addButton.setStyle('margin-right', '50px');
		this.addButton.injectInside(this.tmpDiv);
		
		this.uploadButton = new Element('input');
		this.uploadButton.id = 'upload_submit';
		this.uploadButton.type = 'submit';
		this.uploadButton.value = '上传';
		this.uploadButton.injectInside(this.tmpDiv);
		
		this.z=new Element('div');
		this.z.id = 'upload_0';
		this.z.setStyle('height', '30px');
		this.z.setStyle('margin-top', '8px');
		this.z.innerHTML = '<input type="file" name="file" id="file_0" />';
		this.z.injectInside(this.tmpDiv);
		
		this.maxSize = maxSize;
		this.currentId = 1;
	
		
		$('upload_addButton').onclick = function() {
			this.add();
		}.bind(this);

		this.cancelDiv = new Element("div");
		this.cancelDiv.id = "cancelDiv";
		this.cancelDiv.innerHTML += '<span style="cursor:pointer">取 消</span>';
		this.cancelDiv.injectInside(this.el);
		
		$('cancelDiv').onclick = function() {
			this.cancel();
		}.bind(this);
		
	},
	
	add: function() {
		if (this.currentId >= this.maxSize) {
			alert('不能再增加上传框了，已经达到了设定最大值！');
			return;	
		}
		
		this.create();
		this.currentId++;
	},
	
	create: function() {
		var z = new Element('DIV');
		z.id = 'upload_' + this.currentId;
		z.setStyle('height', '30px');
		z.innerHTML = '<input type="file" name="file" id="file_' + this.currentId + '" style="margin-left:24px;"/><span id="upload_remove_' + this.currentId + '" style="cursor:pointer;margin-left:10px;">去除&nbsp;</span>';
		z.injectInside(this.tmpDiv);
		
		$('upload_remove_' + this.currentId).onclick = function() {
			z.remove();	
			this.currentId--;
		}.bind(this);
	},
	
	onsubmit: function() {
	    var files=0;//记录已选择的文件数
	    var curId = this.currentId;
		for(var i=0;i<curId;i++)
		{
		   if ($('file_'+i).value=="") 
		   {
				$('upload_'+i).remove();//如果文件框显示了，但未选择文件，就删除此输入框	
				this.currentId--;	
		   }else
		   {
		      files++;
		   }
		}
		if (files==0) 
		{
		    this.currentId=0;
		    this.add();
			alert('请选择要上传的文件！');
			return false;
	     }
	     files=0;
		
		this.form.setStyle('display', 'none');
		this.w = new Element('div');
		this.w.id = 'upload_waiting';
		this.w.innerHTML = '<div><img src="images/waiting.gif" />&nbsp;上传中，请等待...</div>'
		this.w.injectInside(this.iframeDiv);
		return true;
		
	},
	
	showResult: function(msg) {
		this.reset();
		eval(msg);
		uploadAreaDialog.close();
	},
	
	cancel: function() {
		this.reset();
		uploadAreaDialog.close();
	},
	
	reset: function() {
		this.iframeDiv.remove();
		this.form.remove();
		//this.w.remove();
		this.cancelDiv.remove();
		uploadArea == null;
		//this.initialize(this.el, this.maxSize);
	}
});