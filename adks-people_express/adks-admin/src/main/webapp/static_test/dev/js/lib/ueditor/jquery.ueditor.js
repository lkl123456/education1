(function($){
  $.fn.extend({
    ueditor:function(customize_options){
      var items = [];
      this.each(function(index){
        var defaults = {
          autosave: {interval: 3000,
                     prefix_name: $(this).attr("id")
                    },
                    image: {
                            file_size_limit: 5242880,//'5 MB',
                            file_types: 'image/jpg,image/gif,image/png,image/jpeg',
                            file_upload_limit: 6,
                            total_file_upload_limit: 100,
                            button_width: "78",
                            button_height: "32",
                            file_types_description : 'JPG Images'
                           },
                    video: {url:'/video_convert',
                            default_image: "/assets/ueditor/default/images/video.png"
                           },
          editor_config_options: {
            minFrameHeight: 480
          }
        }


        var options = $.extend(true, {}, defaults, customize_options);

        var text_area_id = $(this).attr("id");
        var editor = new baidu.editor.ui.Editor(options.editor_config_options);
        editor.render(text_area_id);
        $("#" + text_area_id).removeClass();
        // $(".edui-button-body").tooltip({placement:'bottom'});
        var base_id = editor.uid;
        items.push(editor);


        var link_div = '<div id="ueditor_link_popup' + base_id + '" class="edui-popup-all edui-popup-img" style="display:none;">'
                        + '<div class="edui-popup-background">'
                        + '<div class="edui-popup-all-content">'
                        + '<div class="edui-popup-table">'
                        + '<div class="edui-popup-top" id="ueditor_link_popup_top' + base_id + '">'
                        + '<span class="edui-dialog-caption">添加链接</span>'
                        + '<a href="#" title="关闭" onfocus="this.blur();" class="ueditor_link_close' + base_id + '">关闭</a>'
                        + '</div>'
                        + '<div class="edui-popup-table-content">'
                        + '<input id="ueditor_link_title' + base_id +'" type="text" placeholder="输入链接标题" />'
                        + '<input id="ueditor_link_href' + base_id +'" type="text" placeholder="输入链接地址" />'
                        + '<span id="ueditor_link_error_msg' + base_id + '"></span>'
                        + '<div class="upload-img-list-b">'

                        + '<div class="upload-img-list-b-r">'
                        + '<a href="#" class="ueditor_link_close' + base_id + '" title="取消" onfocus="this.blur();">取消</a>'
                        + '<a href="#" id="ueditor_link_submit' + base_id + '" title="确定" class="edui-upload-button" onfocus="this.blur();">确定</a>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'

        $("#" + text_area_id).before(link_div);

        function insertLink(){
          var href = $('#ueditor_link_href' + base_id).val();
          var obj = {},td,link;
          if(href != ''){
            href = "http://" + href.replace(/^(?:https?:\/\/)|(?:\/)$/ig,"")
          }
          obj.href = href.replace(/^\s+|\s+$/g, '');
          if(!obj.href){
            $("#ueditor_link_error_msg" + base_id).html("请输入链接地址！");
            $('#ueditor_link_popup' + base_id + ' input#ueditor_link_href' + base_id).focus();
          }else{
            obj["target"] = "_blank";
            if($("#ueditor_link_title" + base_id).val()){
              obj.textValue = $("#ueditor_link_title" + base_id).val();
            }
            editor.execCommand('link', obj);
            clearLink();
          }
        }
        function clearLink(){
          $("#ueditor_link_href" + base_id).val("");
          $("#ueditor_link_error_msg" + base_id).html("");
          $("#ueditor_link_popup" + base_id).hide();
        }

        $('#ueditor_link_submit' + base_id).bind('click', function() {
           editor.focus();
           insertLink();
           return false;
        });

        $('.ueditor_link_close' + base_id).bind('click', function() {
           editor.focus();
           clearLink();
           return false;
        });

        var video_div = '<div id="ueditor_video_popup' + base_id + '" class="edui-popup-all edui-popup-img" style="display:none;">'
                        + '<div class="edui-popup-background">'
                        + '<div class="edui-popup-all-content">'
                        + '<div class="edui-popup-table">'
                        + '<div class="edui-popup-top" id="ueditor_video_popup_top' + base_id + '">'
                        + '<span class="edui-dialog-caption">添加视频</span>'
                        + '<a href="#" title="关闭" onfocus="this.blur();" class="ueditor_video_close' + base_id + '">关闭</a>'
                        + '</div>'
                        + '<div class="edui-popup-table-content">'
                        + '<input id="ueditor_video_convert_url' + base_id + '" + value="' + options.video.url + '" style="display:none"/>'
                        + '<input id="ueditor_video_url' + base_id +'" type="text" placeholder="输入视频地址（支持优酷、土豆、酷6、新浪视频）"/>'
                        + '<span id="ueditor_video_error_msg' + base_id + '"></span>'
                        + '<div class="upload-img-list-b">'

                        + '<div class="upload-img-list-b-r">'
                        + '<a href="#" class="ueditor_video_close' + base_id + '" title="取消" onfocus="this.blur();">取消</a>'
                        + '<a href="#" id="ueditor_video_submit' + base_id + '" title="确定" class="edui-upload-button" onfocus="this.blur();">确定</a>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
        $("#" + text_area_id).before(video_div);


        function clearVideo(){
          $("#ueditor_video_url" + base_id).val("");
          $("#ueditor_video_error_msg" + base_id).html("");
          $("#ueditor_video_popup" + base_id).hide();
          remove_loader()
        }

        function remove_loader(){
          $('#ueditor_video_submit' + base_id).removeClass("edui-upload-button-loader")
          $('#ueditor_video_submit' + base_id).remove("span")
          //$('#ueditor_video_submit' + base_id).html('确定');
        }

        function insertVideo() {
          var str = $('#ueditor_video_url' + base_id).val();
          if(str != ''){
            $.ajax({
              type: "POST",
              async: false,
              url: $('#ueditor_video_convert_url' + base_id).val(),
              data: {url : str}
              }).done(function( serverData ) {
                  var jsonStr = jQuery.parseJSON(serverData);
                  if (jsonStr.errormsg == undefined) {
                      var fakedHtml = '<p><img data-video-id="'+jsonStr.attachment_id+'" data-video-url="' + jsonStr.url + '" class="edui-faked-video"' +
                          'data-video-title="' + jsonStr.title + '" src="' + options.video.default_image + '"/></p>' ;
                      editor.execCommand('inserthtml', fakedHtml);
                      clearVideo();
                      editor.focus();
                  } else {
                    remove_loader();
                    $("#ueditor_video_error_msg" + base_id).html(jsonStr.errormsg);
                    $('#ueditor_video_popup' + base_id + ' input#ueditor_video_url' + base_id).focus();
                  }
              });
          }else{
              remove_loader()
              $("#ueditor_video_error_msg" + base_id).html("请输入视频地址！");
              $('#ueditor_video_popup' + base_id + ' input#ueditor_video_url' + base_id).focus();
              return false;
          }
        }


        $('#ueditor_video_submit' + base_id).click(function() {
            $(this).addClass("edui-upload-button-loader")
            $(this).html('');
            $(this).append('确定');
            insertVideo();
            return false;
        });

        $('.ueditor_video_close' + base_id).click(function() {
            editor.focus();
            clearVideo();
            return false;
        });

        var memo = ''
        if(parseInt(options.image.file_upload_limit) < parseInt(options.image.total_file_upload_limit)){
          memo += '每次最多传' + options.image.file_upload_limit +'张，'
        }

        var image_div = '<div id="ueditor_image_popup' + base_id + '" class="edui-popup-all edui-popup-img" style="display:none;">'
                        + '<div class="edui-popup-background">'
                        + '<div class="edui-popup-all-content">'
                        + '<div class="edui-popup-table">'
                        + '<div class="edui-popup-top" id="ueditor_image_popup_top' + base_id + '">'
                        + '<span class="edui-dialog-caption">添加图片</span>'
                        + '<a href="#" title="关闭" onfocus="this.blur();" class="ueditor_image_upload_close' + base_id + '">关闭</a>'
                        + '</div>'
                        + '<div class="edui-popup-table-content">'
                        + '<div class="edui-popup-table-upload">'
                        + '<div id="ueditor_image_upload_btn' + base_id + '"></div>'
                        + '<span>单张最大' + options.image.file_size_limit/1024/1024 + 'MB' +'，' + memo +'总共允许上传' + options.image.total_file_upload_limit + '张。</span>'
                        + '</div>'
                        + '<div class="edui-upload-img-list" id="ueditor_upload_image_progress' + base_id + '"></div>'
                        + '<div id="ueditor_upload_image_thumbnails' + base_id + '" style="display:none"></div>'
                        + '<div class="upload-img-list-b">'
                        + '<div class="upload-img-list-b-l">'
                        + '<input id="ueditor_image_upload_cancel_all' + base_id + '" type="button" value="取消上传" onclick="cancelQueue(swfu);" disabled="disabled" style="display:none" />'
                        + '</div>'
                        + '<div class="upload-img-list-b-r">'
                        + '<a href="#" class="ueditor_image_upload_close' + base_id + '" title="取消" onfocus="this.blur();">取消</a>'
                        + '<a href="#" id="ueditor_image_upload_submit' + base_id + '" title="确定" class="edui-upload-button" onfocus="this.blur();">确定</a>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</div>'

        $("#" + text_area_id).before(image_div);

        var uploader = WebUploader.create({
            dnd: $("#ueditor_image_popup" + base_id)[0],
            disableGlobalDnd: true,
            // 选完文件后，是否自动上传。
            auto: true,
            // swf文件路径
            swf: "/assets/ueditor/third-party/webuploader/Uploader.swf",
            paste: document.body,
            // 文件接收服务端。
            server: options.image.url,
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {id : '#ueditor_image_upload_btn' + base_id, multiple : true},
            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: options.image.file_types
            },
            // 单个文件大小上限
            fileSingleSizeLimit: options.image.file_size_limit
        });
        var fileIds = [];
        var customSettings = {
              progressTarget : 'ueditor_upload_image_progress' + base_id,
              thumbnailsId : 'ueditor_upload_image_thumbnails' + base_id
        };

        uploader.on('beforeFileQueued', function( file ) {
            stats = this.getStats()
            if(stats.successNum + stats.queueNum - stats.cancelNum + 1 > options.image.total_file_upload_limit){
              alert("总共只能上传" + options.image.total_file_upload_limit + '张图片！');
              return false;
            }else if($('.progressWrapper').size() + this.getStats().queueNum >= options.image.file_upload_limit){
              alert("一次只能上传" + options.image.file_upload_limit + '张图片！');
              return false;
            }
        });
        uploader.on('fileQueued', function(file) {
            fileIds.push(file.id);
        });
        uploader.on('uploadProgress', function(file, percentage) {
            var progress = new FileProgress(file, customSettings.progressTarget);
            progress.setProgress(percentage*99);
            progress.setStatus("上传中...");
        });
        uploader.on('uploadSuccess', function(file, response) {
            var progress = new FileProgress(file, customSettings.progressTarget);
            if (response.errormsg != undefined) {
                progress.setError();
                progress.setErrorStatus(response.errormsg);
                progress.toggleCancel(false);
            } else {
                var thumbnailsId = customSettings.thumbnailsId
                progress.setComplete(response);
                addImage(response, thumbnailsId);
                progress.setStatus("上传完成");
                progress.toggleCancel(false);
            }
        });

        uploader.on('error', function(type, file){
            console.log(type)
            var progress = new FileProgress(file, customSettings.progressTarget);
            if(type === 'F_EXCEED_SIZE'){
                progress.setErrorStatus("文件过大.");
                setTimeout(function(){
                    $("#"+file.id).remove();
                }, 3000);
            }
          })

        uploader.on('uploadComplete', function(file){
            var self = this
            $(".edui-imgage-delete").click(function() {
              var thumb = $(this).next();
              var thumbnailsId = customSettings.thumbnailsId
              thumb.parent().prev().remove()
              thumb.parent().remove();
              $("#" + thumbnailsId).find('img[data-image-id=' + thumb.attr("data-image-id") + ']').remove();
              self.removeFile(file);
              return false;
            })
        })

        function addImage(jsonStr, thumbnailsId) {

            var newImg =  '<img src="' + jsonStr.large
                        + '" data-image-original="' + jsonStr.original
                        + '" data-image-large="' + jsonStr.large
                        + '" data-image-id="'+ jsonStr.attachment_id
                        + '" data-image-size="'+ jsonStr.size
                        + '"/>'

            $("#" + thumbnailsId).append(newImg);

        }

        function fadeIn(element, opacity) {
            var reduceOpacityBy = 5;
            var rate = 0;  // 15 fps

            if (opacity < 100) {
                opacity += reduceOpacityBy;
                if (opacity > 100) {
                    opacity = 100;
                }

                if (element.filters) {
                    try {
                        element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
                    } catch (e) {
                        // If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
                        element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
                    }
                } else {
                    element.style.opacity = opacity / 100;
                }
            }

            if (opacity < 100) {
                setTimeout(function () {
                    fadeIn(element, opacity);
                }, rate);
            }
        }

        function FileProgress(file, targetID) {
            this.fileProgressID = file.id;
            this.opacity = 100;
            this.height = 0;

            this.fileProgressWrapper = document.getElementById(this.fileProgressID);
            if (!this.fileProgressWrapper) {
                this.fileProgressWrapper = document.createElement("div");
                this.fileProgressWrapper.className = "progressWrapper";
                this.fileProgressWrapper.id = this.fileProgressID;

                this.fileProgressElement = document.createElement("div");
                this.fileProgressElement.className = "progressContainer";

                var progressCancel = document.createElement("a");
                progressCancel.className = "edui-upload-img-delete";
                progressCancel.href = "#";
                progressCancel.style.visibility = "hidden";
                progressCancel.appendChild(document.createTextNode(" "));

                var progressText = document.createElement("span");
                progressText.style.display = "none";

                var progressBar = document.createElement("span");
                progressBar.className = "progressBarInProgress";

                var progressStatus = document.createElement("span");
                progressStatus.className = "progressBarStatus";
                progressStatus.innerHTML = "&nbsp;";

                this.fileProgressElement.appendChild(progressCancel);
                this.fileProgressElement.appendChild(progressText);
                this.fileProgressElement.appendChild(progressStatus);
                this.fileProgressElement.appendChild(progressBar);

                this.fileProgressWrapper.appendChild(this.fileProgressElement);

                document.getElementById(targetID).appendChild(this.fileProgressWrapper);
            } else {
                this.fileProgressElement = this.fileProgressWrapper.firstChild;
                this.reset();
            }

            this.height = this.fileProgressWrapper.offsetHeight;
            this.setTimer(null);
        }

        FileProgress.prototype.setTimer = function (timer) {
            this.fileProgressElement["FP_TIMER"] = timer;
        };
        FileProgress.prototype.getTimer = function (timer) {
            return this.fileProgressElement["FP_TIMER"] || null;
        };

        FileProgress.prototype.reset = function () {
            this.fileProgressElement.className = "progressContainer";

            this.fileProgressElement.childNodes[2].innerHTML = "&nbsp;";
            this.fileProgressElement.childNodes[2].className = "progressBarStatus";

            this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
            this.fileProgressElement.childNodes[3].innerHTML = "";

            this.appear();
        };

        FileProgress.prototype.setProgress = function (percentage) {
            this.fileProgressElement.className = "progressContainer green";
            this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
            this.fileProgressElement.childNodes[3].innerHTML = percentage + "%";
            this.appear();
        };
        FileProgress.prototype.setComplete = function (jsonStr) {
            this.fileProgressElement.className = "progressContainer blue";
            this.fileProgressElement.childNodes[3].className = "progressBarComplete";
            this.fileProgressElement.childNodes[3].style.width = "";

            var oSelf = this;
            oSelf.disappear();
            var html = '<div><a href="#" class="edui-imgage-delete">取消</a><img src="' + jsonStr.small
                        + '" data-image-original="' + jsonStr.original
                        + '" data-image-large="' + jsonStr.large
                        + '" data-image-id="'+ jsonStr.attachment_id
                        + '" data-image-size="'+ jsonStr.size
                        + '"/>'
            $(oSelf.fileProgressWrapper).after(html)

        };
        FileProgress.prototype.setError = function () {
            this.fileProgressElement.className = "progressContainer red";
            this.fileProgressElement.childNodes[3].className = "progressBarError";
            this.fileProgressElement.childNodes[3].style.width = "";

            var oSelf = this;
            this.setTimer(setTimeout(function () {
                oSelf.disappear();
                $(oSelf.fileProgressWrapper).remove();
            }, 3000));
        };
        FileProgress.prototype.setCancelled = function () {
            this.fileProgressElement.className = "progressContainer";
            this.fileProgressElement.childNodes[3].className = "progressBarError";
            this.fileProgressElement.childNodes[3].style.width = "";

            var oSelf = this;
            this.setTimer(setTimeout(function () {
                oSelf.disappear();
            }, 2000));
        };
        FileProgress.prototype.setStatus = function (status) {
            this.fileProgressElement.childNodes[2].innerHTML = status;
        };

        FileProgress.prototype.setErrorStatus = function (status) {
            this.fileProgressElement.childNodes[2].className = "edui-upload-img-error"
            this.fileProgressElement.childNodes[2].innerHTML = status + '<em></em>';

        };

        // Show/Hide the cancel button
        FileProgress.prototype.toggleCancel = function (show) {
            this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
        };

        FileProgress.prototype.appear = function () {
            if (this.getTimer() !== null) {
                clearTimeout(this.getTimer());
                this.setTimer(null);
            }

            if (this.fileProgressWrapper.filters) {
                try {
                    this.fileProgressWrapper.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 100;
                } catch (e) {
                    this.fileProgressWrapper.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=100)";
                }
            } else {
                this.fileProgressWrapper.style.opacity = 1;
            }

            this.fileProgressWrapper.style.height = "";

            this.height = this.fileProgressWrapper.offsetHeight;
            this.opacity = 100;
            this.fileProgressWrapper.style.display = "";

        };

        FileProgress.prototype.disappear = function () {
            this.fileProgressWrapper.style.display = "none";
        };

        function clearSwfupload(){
            $('#ueditor_upload_image_thumbnails' + base_id).html('');
            $('#ueditor_upload_image_progress' + base_id).html('');
            $('#ueditor_image_popup' + base_id).hide();
        }

        function insertImages(){
          if($('#ueditor_upload_image_thumbnails' + base_id + ' img').length == 0){
            return false;
          }else{
            var html = $('#ueditor_upload_image_thumbnails' + base_id + " img").map(function(){
              this.className = 'upload_img'
              return this.outerHTML
            }).get().join("") + '<br />';
            editor.execCommand("inserthtml", html);
            clearSwfupload();
          }
        }
        $("#ueditor_image_upload_submit" + base_id).click(function() {
            editor.focus();
            insertImages();
            return false;
        });
        $('.ueditor_image_upload_close' + base_id).click(function() {
            editor.focus();
            clearSwfupload();
            return false;
        });

        $('div.edui-popup-all-content').css('top', $(window).height()/2-150);

      });
      return(items);
    }
  });

})(jQuery);
