(function($){
  $.extend($.fn, {
    multiple_upload:function(options){
      var element = this.attr('id');
      var defaults = {
        swf: "/assets/ueditor/third-party/webuploader/Uploader.swf",
        fileSingleSizeLimit: 5242880,
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,png',
            mimeTypes: 'image/jpg,image/gif,image/png,image/jpeg'
        },
        auto: true,
        pick: {id : '#'+element, multiple : true},
        thumb_pick: 'div.uploadimg',
        file_upload_limit: 3,
        // runtimeOrder: 'flash'
      };
      options['server'] = options['server']+'&preview_html=true'
      var setting = $.extend(true, {}, defaults, options);
      var uploader = WebUploader.create(setting);
      uploader.addButton( {id : '#'+element, multiple : true} );
      uploader.on('beforeFileQueued', function( file ) {
          stats = this.getStats();
          if($(setting.thumb_pick).size()  + this.getStats().queueNum >= setting.file_upload_limit){
            alert("一次只能上传" + setting.file_upload_limit + '张图片！');
            return false;
          }
      });
      uploader.on('beforeFileQueued', function( file ) {
        $('#'+element.replace('_button', '_error')).html('');
        $('#'+element.replace('_button', '')).append($('<div id="'+file.id+'" class="swfupload-preview"></div>'));
      });
      uploader.on('uploadProgress', function(file, percentage) {
        $('#'+file.id).html($('<div class="swfupload-progress">已传'+percentage.toFixed(2)*99 + '%</div>'));
      });
      uploader.on('uploadSuccess', function(file, response) {
          if(options.upload_success_handler){
            options.upload_success_handler.call();
          }else{
            $('#'+file.id).replaceWith($(decodeURIComponent(response.preview_html)));
          }
      });
      uploader.on('error', function(type, file){
          $('#'+element.replace('_button', '')).html($('<div id="'+file.id+'" class="swfupload-preview"></div>'))
          if(type == 'F_EXCEED_SIZE'){
            $('#'+file.id).append($('<div class="swfupload-error">尺寸超限</div>'));
          }else{
            $('#'+file.id).append($('<div class="swfupload-error">上传失败</div>'));
          }
          $('#'+file.id).delay(3000).fadeOut('slow');
      })
    },

    single_upload:function(options){
      var element = this.attr('id');
      var defaults = {
        swf: "/assets/ueditor/third-party/webuploader/Uploader.swf",
        fileSingleSizeLimit: 5242880,
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/gif,image/png,image/jpeg'
        },
        auto: true,
        pick: {id : '#'+element, multiple : false},
        // runtimeOrder: 'flash'
      };
      options['server'] = options['server']+'&preview_html=true'
      var setting = $.extend(true, {}, defaults, options);
      var uploader = WebUploader.create(setting);
      uploader.on('beforeFileQueued', function( file ) {
          $('#'+element.replace('_button', '_error')).html('');
          $('#'+element.replace('_button', '')).html($('<div id="'+file.id+'" class="swfupload-preview"></div>'))
      });
      uploader.on('uploadProgress', function(file, percentage) {
        console.log(percentage)
        $('#'+file.id).html($('<div class="swfupload-progress">已传'+percentage.toFixed(2)*99 + '%</div>'));
      });
      uploader.on('uploadSuccess', function(file, response) {
          $('#'+file.id).html($(decodeURIComponent(response.preview_html)));
          if(options.upload_complete_handler){
            options.upload_complete_handler.call();
          }
      });

      uploader.on('error', function(type, file){
        console.log(type)
          $('#'+element.replace('_button', '')).html($('<div id="'+file.id+'" class="swfupload-preview"></div>'))
          if(type == 'F_EXCEED_SIZE'){
            $('#'+file.id).html($('<div class="swfupload-error">尺寸超限</div>'));
          }else if(type == 'F_DUPLICATE'){
            $('#'+file.id).html($('<div class="swfupload-error">文件重复</div>'));
          }else{
            $('#'+file.id).html($('<div class="swfupload-error">上传失败</div>'));
          }
          $('#'+file.id).delay(3000).fadeOut('slow');
      })
    }
  });
})(jQuery);
