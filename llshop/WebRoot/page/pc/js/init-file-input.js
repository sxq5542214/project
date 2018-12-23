//初始化fileinput
var FileInput = function () {
    var oFile = new Object();

    //初始化fileinput控件（第一次初始化）
    oFile.Init = function(ctrlName, uploadUrl,activity_img_url,num) {
    var control = $('#' + ctrlName);
    var img_url = $('#' + activity_img_url);

    //初始化上传控件的样式
    control.fileinput({
        language: 'zh', //设置语言
        uploadUrl: uploadUrl, //上传的地址
        allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
        showUpload: true, //是否显示上传按钮
        showCaption: false,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式     
        dropZoneEnabled: false,//是否显示拖拽区域
        minImageWidth: 50, //图片的最小宽度
        minImageHeight: 50,//图片的最小高度
//        maxImageWidth: 1000,//图片的最大宽度
//        maxImageHeight: 1000,//图片的最大高度
        maxFileSize: 1024,//单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        maxFileCount: num, //表示允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        validateInitialCount:true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
    });

    //导入文件上传完成之后的事件
    $(control).on("fileuploaded", function (event, data, previewId, index) {
    	$(img_url).val(data.response.return_file_url);
    });
    //导入文件上传完成之后的事件
    $(control).on("fileclear", function () {
    	$(img_url).val("");
    });
}
    return oFile;
};