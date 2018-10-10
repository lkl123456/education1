// JavaScript Document
$(document).ready(function(){
	//$('.wenlist').click(function(){
		//$('.main_r_dd2').addClass('main_r_dd3');
		//$('.bian').addClass('bian1');
	//})
	//$('.tuwen').click(function(){
		//$('.main_r_dd2').removeClass('main_r_dd3');
		//$('.bian').removeClass('bian1');
	//})
	$('.main_r_dd1 .kaoshi_2').click(function(){
		$('.bgnb').show();										  
	})
	$('.bg_biao dt b,.zaikao a').click(function(){
		$('.bgnb').hide();							  
	})
	$('.hea_jfe a').click(function(){
		$('.bgna').show();
		return false;
	})
	$('.bg_biao dt b,.zaikao a').click(function(){
		$('.bgna').hide();								  
	})
	$('.xuxiang label').click(function(){
		$kmy=$(this).index('.xuxiang label');
		$(this).find('span').addClass('span1');
		$(this).siblings().find('span').removeClass('span1');
	})
	
	
})
$(document).ready(function(){
	var $m=1;
	var $n=$('.zhengzaixue dd div li').length;
	var $j=Math.ceil($n/6);
	for(var $i=1;$i<=$j;$i++){
		$('.long_n').append('<ul></ul>');
		$('.zhengzaixue dt ul').append('<li></li>');
		}
	$('.zhengzaixue dt ul li').eq(0).addClass('cli');	
/*	if($n<=6){
		$('.zhengzaixue dd div li').slice(0, $n).appendTo('.long_n ul:eq(0)');
		$('.zhengzaixue dt div,.zhengzaixue dt ul').hide();
		}else if($n<=12){
		$('.zhengzaixue dd div li').slice(0, 6).appendTo('.long_n ul:eq(0)');
		$('.zhengzaixue dd div li').slice(0, ($n-6)).appendTo('.long_n ul:eq(1)');	
			}else if($n<=18){
		$('.zhengzaixue dd div li').slice(0, 6).appendTo('.long_n ul:eq(0)');
		$('.zhengzaixue dd div li').slice(0, 6).appendTo('.long_n ul:eq(1)');
		$('.zhengzaixue dd div li').slice(0, ($n-12)).appendTo('.long_n ul:eq(2)');	
			}
			*/
			if($n<=6){
		$('.zhengzaixue dt div,.zhengzaixue dt ul').hide();
		}
			for(var $h=0;$h<$j;$h++){
				$('.zhengzaixue dd div li').slice(0, 6).appendTo('.long_n ul:eq('+($h-1)+')');
				}
			$('.zhengzaixue dd div li').slice(0,($n-($j-1)*6)).appendTo('.long_n ul:eq('+($j-1)+')');	
		$('.qian').click(function(){
			if($m!=1){
				var $ww='-'+(720*($m-2))+'px';
				$('.long_n').stop().animate({'left':$ww})
				$m--;
				$('.zhengzaixue dt ul li').eq($m-1).addClass('cli');
				$('.zhengzaixue dt ul li').eq($m-1).siblings().removeClass('cli');
				}else{
				$('.long_n').stop().animate({'left':'-'+(720*($j-1))+'px'});
				$m=$j;
				$('.zhengzaixue dt ul li').eq($m-1).addClass('cli');
				$('.zhengzaixue dt ul li').eq($m-1).siblings().removeClass('cli');
							}					  
		})
		$('.hou').click(function(){
			if($m<$j){
			var $vv='-'+(720*$m)+'px';
			$('.long_n').stop().animate({'left':$vv});
			$m++;
		$('.zhengzaixue dt ul li').eq($m-1).addClass('cli');
		$('.zhengzaixue dt ul li').eq($m-1).siblings().removeClass('cli');
		}else{
			$('.long_n').stop().animate({'left':'0px'});
			$m=1;
			$('.zhengzaixue dt ul li').eq($m-1).addClass('cli');
			$('.zhengzaixue dt ul li').eq($m-1).siblings().removeClass('cli');
			}						 
		})
		$('.zhengzaixue dt ul li').click(function(){
			var $kb=$(this).index();
			$(this).addClass('cli');
			$(this).siblings().removeClass('cli');
			$('.long_n').stop().animate({'left':'-'+(720*$kb)+'px'});
			$m=$kb+1;
		})
		var $mv=$('.nav li').length;
		var $mc=Math.floor(1000/$mv);
		$('.nav li').width($mc);
})

