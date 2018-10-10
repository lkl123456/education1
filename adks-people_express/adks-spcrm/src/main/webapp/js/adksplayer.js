//是否正在拖动
var IsChangingPosition = false;
var adksplayer = {};
if (adksplayer)
    {
    /*DATAARRAY_BEGIN*/adksplayer.pptList=[{time:'0',url:'ppt/page1.jpg'},{time:'72.155006',url:'ppt/page2.jpg'},{time:'135.292007',url:'ppt/page3.jpg'},{time:'171.37001',url:'ppt/page4.jpg'},{time:'284.106018',url:'ppt/page5.jpg'},{time:'513.116028',url:'ppt/page6.jpg'},{time:'556.038025',url:'ppt/page7.jpg'},{time:'618.470032',url:'ppt/page8.jpg'},{time:'723.825012',url:'ppt/page9.jpg'},{time:'835.03302',url:'ppt/page10.jpg'},{time:'926.730042',url:'ppt/page11.jpg'},{time:'979.795044',url:'ppt/page12.jpg'},{time:'1058.428101',url:'ppt/page13.jpg'},{time:'1165.769043',url:'ppt/page14.jpg'},{time:'1185.739014',url:'ppt/page15.jpg'},{time:'1276.854004',url:'ppt/page16.jpg'},{time:'1411.654053',url:'ppt/page17.jpg'},{time:'1474.061035',url:'ppt/page18.jpg'},{time:'1535.543091',url:'ppt/page19.jpg'},{time:'1597.724121',url:'ppt/page20.jpg'},{time:'1675.713135',url:'ppt/page21.jpg'},{time:'1789.535034',url:'ppt/page22.jpg'},{time:'1976.07605',url:'ppt/page23.jpg'},{time:'2036.149048',url:'ppt/page24.jpg'},{time:'2107.815186',url:'ppt/page25.jpg'},{time:'2225.853027',url:'ppt/page26.jpg'},{time:'2260.63208',url:'ppt/page27.jpg'},{time:'2434.526123',url:'ppt/page28.jpg'},{time:'2520.947021',url:'ppt/page29.jpg'},{time:'2603.1521',url:'ppt/page30.jpg'},{time:'2657.955078',url:'ppt/page31.jpg'},{time:'2735.944092',url:'ppt/page32.jpg'},{time:'2792.008057',url:'ppt/page33.jpg'},{time:'2990.329102',url:'ppt/page34.jpg'},{time:'3074.39209',url:'ppt/page35.jpg'},{time:'3107.037109',url:'ppt/page36.jpg'},{time:'3257.843262',url:'ppt/page37.jpg'}];    adksplayer.chapterList=[{time:'0',title:''},{time:'0',title:'&nbsp;&nbsp;党的基本知识（下）'},{time:'72.155006',title:'&nbsp;&nbsp;三、中国共产党的自身建设'},{time:'77.155006',title:'&nbsp;&nbsp;（一）基本要求：围绕中心任务进行党的建设'},{time:'171.37001',title:'&nbsp;&nbsp;（二）党的建设的内容'},{time:'3257.843262',title:'&nbsp;&nbsp;欢迎您学习其它课程'},{time:'3260.722168',title:''}];/*DATAARRAY_END*/
    var playTime = 0,
        nowTime = null,//播放时间
        pptIndex = 0,
        chapterIndex = 0,
        playerDuration = 0,
        isMaxPosition = false,
        maxPosition = 0,
        myJwPlayer = null,
        myPPTBox = null,
        myChapterBox = null,
        myTimeoutNumber=0,
        //每秒事件
        EverySecondFunctionList = [],
        TimePointFunctionList = [],
        LoadCompleteList = [],
        //比较时间是否相等
        eqTime = function (a, b) {
            var na = parseInt(Number(a));
            var nb = parseInt(b);
            return na == nb;
        },
        converTime = function (l) {
            var seconde = Number(l),
               hour = parseInt(seconde / (60 * 60)),
               minute = parseInt(seconde / 60);
            seconde = parseInt(seconde % 60);
            return (hour < 10 ? '0' + hour : hour) + ':' + (minute < 10 ? '0' + minute : minute) + ':' + (seconde < 10 ? '0' + seconde : seconde);
        },
        //循环事件
        run = function () {
            setTimeout(function () {
                PlayingHandler(adksplayer.pptList);
            }, 1100);
        },
        //事件触发
        PlayingHandler = function (list) {
            var t = adksplayer.getPosition();
            for (var id in EverySecondFunctionList) {
                EverySecondFunctionList[id](t);
            }

            for (var i = 0; i < list.length; i++) {
                if (eqTime(list[i].time, t)) {
                    for (var j = 0; j < TimePointFunctionList.length; j++) {
                        TimePointFunctionList[j](t);
                    }
                    break;
                }
            }
            run();
        },
        //记录时间
        startTiming = function () {
            if (nowTime == null) {
                nowTime = new Date().getTime();
            }
            playTime = playTime + (new Date().getTime() - nowTime);
            nowTime = new Date().getTime();
           // playTime += 100;
        },
        //监听加载完成事件
        ListenComplete = function () {
            if (playerDuration == 0) {
                setTimeout(ListenComplete, 1000);
            }
            else {
                for (var id in LoadCompleteList) {
                    LoadCompleteList[id]();
                }
            }
        },
        checkUserLive = function (t) {
            if (myJwPlayer.getState() == 'PLAYING') {
                var playSecond = Math.round(playTime / 1000);
                if (playSecond % 600 == 0 && playSecond != 0) {
                    pauseClick();
                    //为了不让同一秒内弹出两次
                    playTime += 1000;
                    //myTimeoutNumber = setTimeout (doQuit, 5000);
                    adksplayer.showMessage('请点击【继续学习】按钮继续观看课程！', '继续学习', function () {
                        playClick();
                        //clearTimeout(myTimeoutNumber);
                    });

                }
            }
        };
    
    var BarPosition = null,
        BarDuration = null,
        BarPlay = null,
        BarPause = null,
        BarStop = null,
        BarChapterTitle = null,
        playClick = function () {
            adksplayer.MP4.pause();
            BarPlay.hide();
            BarPause.show();
        },
        pauseClick = function () {
            adksplayer.MP4.pause();
            BarPause.hide();
            BarPlay.show();
        },
        stopClick = function () {
            adksplayer.MP4.stop();
            BarPause.hide();
            BarPlay.show();
        },
        checkState = function () {
            if (adksplayer.MP4.getState() == 'PLAYING') {
                BarPlay.hide();
                BarPause.show();
            }
            else {
                BarPause.hide();
                BarPlay.show();
            }
        };
               
    //ppt
    (function (player) {
        var pptPlayer = player.PPT = {};
        var list = player.pptList;
        pptPlayer.init = function () {
            //$('#pptbox').html('<li><img  id="PPT" src=""></li>');
            //pptPlayer.onChange(0);
        };

        pptPlayer.showPPT = function (imgurl,left) {
            var childCount = myPPTBox.children().length;
            if (childCount == 1) {
                myPPTBox.append('<li><img  src="' + imgurl + '"></li>');
                var first = myPPTBox.children().eq(0);
                var last = myPPTBox.children().eq(1);
                if (left) {
                    first.animate({ 'left': '-100%', 'opacity': 0}, 800, null, function () { first.remove(); });
                    last.animate({  'opacity': 1 }, 300);
                }
                else {
                    first.animate({ 'left': '200%', 'opacity': 0 }, 800, null, function () { first.remove(); });
                    last.animate({ 'opacity': 1 }, 300);
                }
            }

            if (childCount == 0) {
                myPPTBox.append('<li><img  src="' + imgurl + '"></li>');
                var first = myPPTBox.children().eq(0).animate({ 'opacity': 1 }, 300);
            }
        };

        //随时间改变
        pptPlayer.onChange = function (t) {
            for (var i in list) {
                var item = list[i];
                if (eqTime(item.time, t)) {
                    pptIndex = i;
                    //pptPlayer.showPPT(item.url);
                    myPPTBox.html('<li><img  src="' + item.url + '"></li>');
                    myPPTBox.children().eq(0).css({ 'opacity': 1 });
                    break;
                }
            }
        };
        //下一个
        pptPlayer.next = function () {
            var nextIndex = parseInt(pptIndex) + 1;
            if (list.length > nextIndex) {
                pptPlayer.showPPT(list[nextIndex].url,false);
                pptIndex = nextIndex;
            }
        };
        //上一个
        pptPlayer.previous = function () {
            var preIndex = parseInt(pptIndex) - 1;
            if (preIndex > -1) {
                pptPlayer.showPPT(list[preIndex].url,true);
                pptIndex = preIndex;
            }
        };
        //同步
        pptPlayer.synchronous = function (t) {
            var pptCount = list.length;
            var currentPostion = t;
            if (pptCount > 0) {
                if (arguments.length < 1) {
                    currentPostion = player.getPosition();
                }
                for (var i = 0; i < pptCount - 1; i++) {
                    if (Number(list[i].time) <= currentPostion &&
                        Number(list[i + 1].time) > currentPostion) {
                        //pptPlayer.showPPT(list[i].url, false);
                        myPPTBox.html('<li><img  src="' + list[i].url + '"></li>');
                        myPPTBox.children().eq(0).css({ 'opacity': 1 });
                        break;
                    }
                }
            }
        }

        pptPlayer.currentTime = function (t) {
            $('#currentTime').html(converTime(t));
        };

       // pptPlayer.init();
        TimePointFunctionList.push(pptPlayer.onChange);

    })(adksplayer);

    //chapter
    (function (player) {
        var chapter = player.Chapter = {};
        chapter.chapterIndex = 0;
        var chapterString = [];
        var temp = '<li> <a time="{0}" href="javascript:adksplayer.chapterSeek({2});">{1}</a></li>';
        var chapterlist = player.chapterList;
        var makeList = function (list) {
            for (var i in list) {
                var chapter = list[i];
                if(chapter.title.length>0){
                chapterString.push(temp.replace('{0}', chapter.time).replace('{1}', chapter.title).replace('{2}', chapter.time));//.replace('{2}', convertTime(chapter.time)));
            }}
        }

        chapter.Init = function () {
            makeList(chapterlist);
            $('#chapterbox').html(chapterString.join(''));
        };

        chapter.onChange = function (t) {
            for (var i = 0; i < chapterlist.length; i++) {
                if (eqTime(chapterlist[i].time, t)) {
                    chapterIndex = i;
                    $('#chapterbox  li a').removeClass('active');
                    $('#chapterbox li a[time="' + chapterlist[i].time + '"]').addClass('active');
                    BarChapterTitle.html(chapterlist[i].title);
                    break;
                }
            }
        };

        chapter.synchronous = function (t) {
            var chapterCount = chapterlist.length;
            var currentPostion = t;
            if (chapterCount > 0) {
                if (arguments.length < 1) {
                    currentPostion = getCurrentPosition();
                }
                for (var i = chapterCount - 1; i > 0  ; i--) {
                    if (Number(chapterlist[i].time) <= currentPostion) {
                        chapterIndex  = i;
                        $('#chapterbox  li a').removeClass('active');
                        $('#chapterbox li a[time="' + chapterlist[i].time + '"]').addClass('active');
                        BarChapterTitle.html(chapterlist[i].title);
                        break;
                    }
                }
            }
        };

        
        //chapter.Init();
        TimePointFunctionList.push(chapter.onChange);
    })(adksplayer);
    //mp4
    (function (player) {
        var mp4 = player.MP4 = {};
        mp4.play = function () {
            //jwplayer('player').play(true);
            myJwPlayer.play(true);
        };
        mp4.pause = function () {
            var b = mp4.getState() == 'PLAYING';
            // jwplayer('player').pause(b);
            myJwPlayer.pause(b);
        };
        mp4.stop = function () {
            //jwplayer('player').stop();
            myJwPlayer.stop();
        };
        mp4.seek = function (t) {
            //setTimeout(jwplayer('player').seek, 1000, t);
            myJwPlayer.seek(t);
        };
        mp4.getState = function () {
            //return jwplayer('player').getState();
            return myJwPlayer.getState();
        };
        mp4.getDuration = function () {
            // return jwplayer('player').getDuration();
            return myJwPlayer.getDuration();
        };
    })(adksplayer);

    adksplayer.chapterSeek = function (position) {
        if (isMaxPosition) {
            if (position > maxPosition) {
                alert('尊敬的学员，请勿在未学部分拖动学习！');
                return;
            }
        }
        // jwplayer('player').seek(position);
        myJwPlayer.seek(position);
        adksplayer.PPT.onChange(position);
        adksplayer.Chapter.onChange(position);
    };

    adksplayer.chapterNext = function () {
        var nextIndex = parseInt(chapterIndex) + 1;
        if (adksplayer.chapterList.length > nextIndex) {
            adksplayer.chapterSeek(adksplayer.chapterList[nextIndex].time);
            chapterIndex = nextIndex;
        }
    };
 
    adksplayer.getPlayTime = function () {
        return Math.round(playTime / 1000);
    };

    adksplayer.getPosition=function()
    {
        if (myJwPlayer != null)
        {
            return myJwPlayer.getPosition();
        }
        //var t = jwplayer('player').getPosition();
        //return t;
    };

    adksplayer.setMaxPosition = function (t) {
        isMaxPosition = true;
        maxPosition = t;
    };

    adksplayer.getDuration = function () {
        return playerDuration;
    };

    adksplayer.setVolume = function (t) {
        // jwplayer('player').setVolume(t);
        myJwPlayer.setVolume(t);
    };

    adksplayer.getVolume = function () {
        //return jwplayer('player').getVolume();
        return myJwPlayer.getVolume();
    };

    adksplayer.showMessage = function (msg,buttonTex,callback) {
        var mask = $('.mask');
        var tip = $('.tips');
        tip.html('<p>' + msg + '</p>' + '<a href="javascript:void(0);" id="msgbt">' + buttonTex + '</a>');
        $('#msgbt').click(
            function () {
                $('.mask').hide();
                callback()
            });
        mask.show();
    };

    adksplayer.Seek = function (position) {
        if (isMaxPosition) {
            if (position > maxPosition)
            {
                alert('尊敬的学员，请勿在未学部分拖动学习！');
                return;
            }
        }
        //不能大过总时长
        var t = parseInt(Math.min(playerDuration - 1, position));
        adksplayer.Chapter.synchronous(t);
        adksplayer.PPT.synchronous(t);
       
        //jwplayer('player').seek(t);
        myJwPlayer.seek(t);
        //按钮重置
        BarPlay.hide();
        BarPause.show();
    };
    //设置进度lable
    adksplayer.setPosition = function (p, d) {
        if (arguments.length > 1) {
            playerDuration = d;
            BarDuration.html(converTime(d));
        }
        BarPosition.html(converTime(p));
    };

    adksplayer.addTimeEvent = function (f) {
        EverySecondFunctionList.push(f);
    };

    adksplayer.addLoadComplete = function (f)
    {
        LoadCompleteList.push(f);
    };

    adksplayer.setup = function (file, autostart,checklive) {
        myPPTBox = $('#pptbox');
        BarPosition = $('#BarPosition');
        BarDuration = $('#BarDuration');
        BarPlay = $('#BarPlay');
        BarPause = $('#BarPause');
        BarStop = $('#BarStop');
        BarChapterTitle = $('#chapterTitle');
        BarPlay.click(playClick);
        BarPause.click(pauseClick);
        BarStop.click(stopClick);
        if (!autostart)
        {
            BarPause.hide();
            BarPlay.show();
        }
        //初始化ppt chapter
        adksplayer.Chapter.Init();
        adksplayer.PPT.init();
        //检查用户是否还在学习
        if (checklive)
        {
            EverySecondFunctionList.push(checkUserLive);
        }

        myJwPlayer = jwplayer('video').setup({
            file: file,
            provider: 'http',
            autostart: autostart,
            html5player: 'script/jwplayer.html5.js',
            flashplayer: 'script/Player.swf',
            height: '100%',
            width: '100%',
            startparam: 'start'
            //,controls: false
        }).onTime(function (e) {
            startTiming();
            if (!IsChangingPosition) {
                adksplayer.setPosition(e.position, e.duration);
            }
            //设置当前可以播放的时间
            maxPosition = Math.max(e.position, maxPosition);
        }).onSeek(function (e) {
            //alert('p:'+e.position+',o:'+e.offset);
           // adksplayer.Chapter.synchronous(e.offset);
          //  adksplayer.PPT.synchronous(e.offset);
        }).onComplete(function () {
            //按钮重置
            BarPause.hide();
            BarPlay.show();
            //进度重置
            adksplayer.setPosition(0, playerDuration);
            nowTime = null;
        }).onPlay(function () {
            BarPlay.hide();
            BarPause.show();
            nowTime = null;
        }).onPause(function () {
            BarPause.hide();
            BarPlay.show();
            nowTime = null;
        });
    };

    //触发循环监听
    run();

    //开始监听是否加载完成
    ListenComplete();
   

}//end if

(function ($) {
    $.extend($.fn, {
        ///<summary>
        /// apply a slider UI
        ///</summary>
        jSlider: function (setting) {
            var ps = $.extend({
                //content holder(Object || css Selector)
                renderTo: $(document.body),
                //whether the slider can be dragged
                enable: true,
                //'max' or 'min'
                initPosition: 'max',
                //width of bar and slider
                size: { barWidth: 200, sliderWidth: 5 },
                //class name of bar
                barCssName: 'defaultbar',
                //class name of completed bar
                completedCssName: 'jquery-completed',
                //class name of slider
                sliderCssName: 'jquery-jslider',
                //class name of slider when mouse over
                sliderHover: 'jquery-jslider-hover',
                //fired when the users are dragging the slider
                onChanging: function () { },
                //fired when the users doppped the slider
                onChanged: function () { }
            }, setting);

            ps.renderTo = (typeof ps.renderTo == 'string' ? $(ps.renderTo) : ps.renderTo);

            /* ---------->
            html tree:
            <div> ---->sliderbar
            <div>&nbsp;</div>   ----> completed bar
            <div>&nbsp;</div>   ----> slider                  
            </div>
            <-----------*/
            var sliderbar = $('<div><div>&nbsp;</div><div>&nbsp;</div></div>')
                                .attr('class', ps.barCssName)
                                    .css('width', ps.size.barWidth)
                                        .appendTo(ps.renderTo);

            var completedbar = sliderbar.find('div:eq(0)')
                                    .attr('class', ps.completedCssName);

            var slider = sliderbar.find('div:eq(1)')
                            .attr('class', ps.sliderCssName)
                                .css('width', ps.size.sliderWidth);

            var bw = sliderbar.width(), sw = slider.width();
            //make sure that the slider was displayed in the bar(make a limited)
            ps.limited = { min: 0, max: bw - sw };

            if (typeof window.$sliderProcess == 'undefined') {
                window.$sliderProcess = new Function('obj1', 'obj2', 'left',
                                                 'obj1.css(\'left\',left);obj2.css(\'width\',left);');
            }
            $sliderProcess(slider, completedbar, eval('ps.limited.' + ps.initPosition));

            //drag and drop
            var slide = {
                drag: function (e) {
                    var d = e.data;
                    var l = Math.min(Math.max(e.pageX - d.pageX + d.left, ps.limited.min), ps.limited.max);

                    $sliderProcess(slider, completedbar, l);
                    //push two parameters: 1st:percentage, 2nd: event
                    ps.onChanging(l / ps.limited.max, e);
                },
                drop: function (e) {
                    slider.removeClass(ps.sliderHover);
                    //push two parameters: 1st:percentage, 2nd: event
                    ps.onChanged(parseInt(slider.css('left')) / ps.limited.max, e);

                    $(document).unbind('mousemove', slide.drag).unbind('mouseup', slide.drop);
                }, up: function (e)
                {
                    ps.onChanged(e.data / ps.limited.max, e);
                    sliderbar.unbind('mouseup', slide.up);
                }
            };
           
            if (ps.enable) {
                slider.bind('mousedown', function (e) {
                    var d = {
                        left: parseInt(slider.css('left')),
                        pageX: e.pageX
                    };
                    $(this).addClass(ps.sliderHover);
                    $(document).bind('mousemove', d, slide.drag).bind('mouseup', d, slide.drop);
                });
                sliderbar.bind('mousedown', function (e) {
                    var l = e.clientX - sliderbar.offset().left;
                    $sliderProcess(slider, completedbar, l);
                    ps.onChanging(l / ps.limited.max, e);
                    sliderbar.bind('mouseup', l, slide.up)
                });
            }
            slider.data = { bar: sliderbar, completed: completedbar };
            return slider;
        },
        ///<summary>
        /// set slider value
        ///</summary>
        ///<param name="v">percentage, must be a Float variable between 0 and 1</param>
        ///<param name="v">callback Function</param>
        setSliderValue: function (v, callback) {
            try {
                //validate
                if (typeof v == 'undefined' || v < 0 || v > 1) {
                    //throw new Error('\'v\' must be a Float variable between 0 and 1.');
                    return;
                }

                var s = this;

                //validate 
                if (typeof s == 'undefined' ||
                    typeof s.data == 'undefined' ||
                        typeof s.data.bar == 'undefined') {
                    throw new Error('You bound the method to an object that is not a slider!');
                }

                $sliderProcess(s, s.data.completed, v * s.data.bar.width());

                if (typeof callback != 'undefined') { callback(v); }
            }
            catch (e) {
                alert(e.message);
            }
        }
    });
})(jQuery);


$(document).ready(function () {
    $('.mPPT').hover(function () {
        $('.pptOpt a').show();
    }, function () {
        $('.pptOpt a').hide();
    });

    document.onselectstart = new Function('return false');

    //视频文件名、是否自动播放、是否检测正在学习
    adksplayer.addLoadComplete(loadPage);

    var videoServer = "";

    var videoServerStr = window.location.search;
	if (typeof (videoServerStr) != 'undefined' && videoServerStr != null
			&& videoServerStr != "" ) {
        var videoServerArray = videoServerStr.split("=");
        if (typeof (videoServerArray) != 'undefined' && videoServerArray != null
            && videoServerArray.length >0 && typeof(videoServerArray[1]) != 'undefined'
			&& videoServerArray[1] != null && videoServerArray[1].length > 0) {
            videoServer = videoServerArray[1];
        }
    }

    //视频文件名、是否自动播放、是否检测正在学习
    adksplayer.setup(videoServer + 'video/a.mp4', true, true);

    var myBar = $.fn.jSlider({
        renderTo: '#BarSilder',
        initPosition: 'min',
        enable: true,
        size: { barWidth: '100%', sliderWidth: 12 },
        onChanging: function (percentage, e) {
            //开始拖动
            IsChangingPosition = true;
            adksplayer.setPosition(adksplayer.getDuration() * percentage);
        },
        onChanged: function (percentage, e) {
            //结束拖动
            IsChangingPosition = false;
            adksplayer.Seek(adksplayer.getDuration() * percentage);
        }
    });
    var volBar = $.fn.jSlider({
        renderTo: '#BarVolume',
        initPosition: 'min',
        enable: true,
        barCssName: 'volumebar',
        size: { barWidth: '100%', sliderWidth: 12 },
        onChanging: function (percentage, e) {
            adksplayer.setVolume(parseInt(100 * percentage));
        }
    });

    adksplayer.addTimeEvent(function (t) {
        var totalTime = adksplayer.getDuration();
        if (!IsChangingPosition) {
            myBar.setSliderValue(t / totalTime);
        }

        //同步音量
        volBar.setSliderValue(adksplayer.getVolume() / 100);

        //每10%提交一次，总学习时长
        //var sessionTime = adksplayer.getPlayTime();
        //var tenpercent = parseInt(totalTime / 10);
        //if (sessionTime % tenpercent == 0) {
        //    setSessionTime(sessionTime);
        //    setLessonLocation(t);
        //}
        var sessionTime = adksplayer.getPlayTime();
        setSessionTime(sessionTime);
        setLessonLocation(t);
		hasFocus_Event();
    });





   

    $("a.optDoc").click(function () {
        $("#body").addClass("PtD");
        $("#body").removeClass("PtV");
        $("a.optDoc").hide();
        $("a.optPPT").show();
    })

    $("a.optPPT").click(function () {
        $("#body").removeClass("PtD");
        $("#body").removeClass("PtV");
        $("a.optDoc").show();
        $("a.optPPT").hide();
    })


    $("a#optModeP").click(function () {
        if ($("#body").hasClass("PtV")) {
            $("#body").removeClass("PtV");
            $("#body").removeClass("PtD");
            $(this).addClass('optModeA').removeClass('optModeB');
        }
        else {
            $("#body").addClass("PtV");
            $("#body").removeClass("PtD");
            $(this).addClass('optModeB').removeClass('optModeA');
            if ($('a.optPPT').is(':visible') == false) {
                $("a.optDoc").show();
                $("a.optPPT").hide();
            }
            
        }
    })

    $('.mTag li').each(function (i, n) {
        $(n).click(function () {
            $('.mTag li').removeClass('On');
            $(this).addClass('On');
            $('.sd').removeClass('appActive');
            $('.' + $(this).attr('tag')).addClass('appActive');
        });
    });

});


var doQuit = function () {
    window.onbeforeunload = null;
    unloadPage();
    var browserName = navigator.appName;
    if (browserName == "Netscape") {
        window.open('', '_self', '');
        window.close();
    }
    else {
        if (browserName == "Microsoft Internet Explorer") {
            window.opener = "whocares";
            window.opener = null;
            window.open('', '_top');
            window.close();
        }
    }
};

////////////////////////////////////////////////

var focus = true;//默认获得焦点

var switch_focus = true;//焦点开关

//网页失去焦点执行
function hasFocus_Event(){
	
	if(switch_focus){
		var currentFocus = document.hasFocus();
		if(focus!=currentFocus){
			if(!currentFocus){
				if(myJwPlayer.getState() == 'PLAYING'){
					pauseClick();
				}else{
					playClick();
				}
			}
			focus = currentFocus;
		}
	}
}


