var UPLOAD_TYPE_IMAGE = 'i', UPLOAD_TYPE_FILE = 'f';
var DEFAULT_LIMIT = 20971520;

function Attachment(seq, path, name, size, type, secure){
	
	this.seq = seq;
	this.path = path;
	this.name = name;
	this.size = size;
	this.type = type;
	this.secure = secure;	
	
	this.equals = function(obj){
		
		if(!(obj instanceof Attachment))throw "Wrong Data Type";
		return (this.seq == obj.seq);		
		
	}
	
}



var Uploader = {

	callerSelector:'#attachment_caller',
	disposerSelector:'#attachment_disposer',
	editorID:'contents_with_markup',
	directory:'/',
	deleteButton:'[삭제]',
	tableSelector:null,
	hash:null,
	inputs:null,
	paramName:null,
	limits:null,
	sum:null,
	uriHeader:'',

	init:function(){

		this.tableSelector = {};
		this.tableSelector[UPLOAD_TYPE_IMAGE] = '#attached_images';
		this.tableSelector[UPLOAD_TYPE_FILE] = '#attached_files';
		
		this.paramName = {};
		this.paramName[UPLOAD_TYPE_IMAGE] = 'images';
		this.paramName[UPLOAD_TYPE_FILE] = 'files';
		
		this.hash = {};
		this.hash[UPLOAD_TYPE_IMAGE] = [];
		this.hash[UPLOAD_TYPE_FILE] = [];
		
		this.inputs = {};
		this.inputs[UPLOAD_TYPE_IMAGE] = [];
		this.inputs[UPLOAD_TYPE_FILE] = [];		

		this.limits = {};
		this.limits[UPLOAD_TYPE_IMAGE] = DEFAULT_LIMIT;
		this.limits[UPLOAD_TYPE_FILE] = DEFAULT_LIMIT;

		this.sum = {};
		this.sum[UPLOAD_TYPE_IMAGE] = 0;
		this.sum[UPLOAD_TYPE_FILE] = 0;

	},

	open:function(type){

		var objForm = $(this.callerSelector);
		
		$('input[name=\'attachment.uploadType\']', objForm).val(type);
		objForm.attr('action', this.directory +'attachments/new.action');
		
		objForm.ajaxSubmit({
//			beforeSubmit:function(params){
//				alert($.param(params));
//			},
			success:function(xhr){
				var attachmentContiner = $('<div>'+ xhr + '</div>');
				attachmentContiner.css('cursor', 'default');
				$.blockUI({message:attachmentContiner});			
			},
			error:function(xhr){
				if(xhr.status == 401){
					alert(appMessage.loginRequired);
					location.href = Uploader.directory + 'sessions/new.action';
				}else{
					alert(appMessage.requestHttpError.replace('?', xhr.status));
				}
			}
		});
		
	},

	callback:function(seq, path, name, size, type, secure){

		var attachment = new Attachment(seq, path, name, size, type, secure);

		switch(type){

			case UPLOAD_TYPE_IMAGE : 

				attachment.type = UPLOAD_TYPE_IMAGE;
				this.attachImage(attachment);
				break;

			case UPLOAD_TYPE_FILE : 

				attachment.type = UPLOAD_TYPE_FILE;
				this.attachFile(attachment);
				break;

		}
		
		$.unblockUI();

	},

	attachImage:function(att){
		var oEditor = null;
		try{
			oEditor = FCKeditorAPI.GetInstance(this.editorID);
		}catch(e){};

		if(oEditor){

			var imgTag = "<img src='"+ this.uriHeader + att.path +"'/>";

			if(oEditor.EditMode == FCK_EDITMODE_WYSIWYG ){

				oEditor.InsertHtml(imgTag);

			}else{

				oEditor.SetHTML(oEditor.GetHTML(true) + imgTag);

			};

		};

		this.listUp(att);

	},

	attachFile:function(att){
		this.listUp(att);
	},

	detach:function(seq, type){

		if(!confirm('삭제된 파일은 복구할 수 없습니다.\n\n정말로 삭제하시겠습니까?'))return true;
		
		var att = null;
		$(this.hash[type]).each(function(){
			if(this.seq == seq){
				att = this;
				return false;
			}
		});

		if(att){
			
			var objForm = $(this.disposerSelector);
			$('input[name=\'attachment.seq\']', objForm).val(att.seq);
			
			objForm.ajaxSubmit({
				beforeSend:function(xhr){
					$.blockUI({message:null});
				},				
				success:function(xhr){
			
					if(att.type == UPLOAD_TYPE_IMAGE){

						var oEditor = null;
						try{
							oEditor = FCKeditorAPI.GetInstance(Uploader.editorID);
						}catch(e){};
					
						if(oEditor){

							$(oEditor.EditorDocument.images).each(function(){
								if(this.src.substring(this.src.length - att.path.length, this.src.length) == att.path)$(this).remove();
								//if(eval('/'+ att.path.split("/").join("\/") +'$/').test(this.src))$(this).remove();
								//if(this.src.indexOf(att.path) > -1)$(this).remove();
							});

						}

					}

					Uploader.listDown(att);		
					$.unblockUI();
					
				},
				error:function(xhr){
					alert(appMessage.requestHttpError.replace('?', xhr.status));
					$.unblockUI();
				}
			});

			
			return true;
		}

		return false;

	},

	listUp:function(att){

		this.hash[att.type].push(att);
		this.sum[att.type] = this.sum[att.type] + parseInt(att.size);

		var infoTable = null;
		try{
			infoTable = $(this.tableSelector[att.type]);
		}catch(e){};

		if(infoTable.length > 0){

			var row = null, cell = null, a = null, inputs = {seq:null, secure:null};
			
			row = $('<tr id="attachment_row_'+ att.seq +'"/>');
			infoTable.append(row);
			
			cell = $('<td class="attachment_name"/>');
			row.append(cell);

			inputs.seq = $('<input type="hidden" value="'+ att.seq +'"/>');
			inputs.seq.attr('name', this.paramName[att.type] +'['+ (this.hash[att.type].length - 1) +'].seq');
			cell.append(inputs.seq);
			
			inputs.secure = $('<input type="hidden" value="'+ att.secure +'"/>');
			inputs.secure.attr('name', this.paramName[att.type] +'['+ (this.hash[att.type].length - 1) +'].secure');
			cell.append(inputs.secure);

			cell.append('<a href="'+ (this.directory+"attachments/show.action?attachment.seq="+ att.seq +'&force=true') +'">'+ att.name +'</a>');

			cell = $('<td class="attachment_size">'+ calCapacity(att.size) +'</td>');
			row.append(cell);

			cell = $('<td class="attachment_button"/>');
			row.append(cell);

			a = $('<a href="#">'+ this.deleteButton +'</a>');
			cell.append(a);
			
			a.click(function(){
				if(!Uploader.detach(att.seq, att.type))$(this).parents('tr').remove();
				return false;
			});
			
			this.inputs[att.type].push(inputs);

		}

	},

	listDown:function(att){
		
		var atts = this.hash[att.type], inputs = this.inputs[att.type];
		this.hash[att.type] = [], this.inputs[att.type] = [];
		$(atts).each(function(index){
			if(!this.equals(att)){
				Uploader.hash[att.type].push(this);
				
				var pref = Uploader.paramName[att.type] +'['+ (Uploader.hash[att.type].length - 1) +']';
				inputs[index].seq.attr('name', pref + '.seq');
				inputs[index].secure.attr('name', pref + '.secure');
				Uploader.inputs[att.type].push(inputs[index]);
			}
		});
		
		this.sum[att.type] = this.sum[att.type] - parseInt(att.size);

		var infoTable = null;
		try{
			infoTable = $(this.tableSelector[att.type]);
		}catch(e){};

		if(infoTable.length > 0)$('#attachment_row_'+ att.seq, infoTable).remove();

		att = null;

	}

};

Uploader.init();
