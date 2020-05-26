$(function () {
    $.ajax({
        url: ctx + 'system/notice/noticePhoto',
        type: "post",
        dataType: "json",
        success: function (result) {
            var doc = $("#slider");
            doc.empty();
            var str = '';
            $.each(result,function(index,data){
                if(data.noticePhoto.indexOf(".pdf")<=0){
                    str += '<a href="javascript:void(0)" onclick="noticeDetail(\''+data.noticeId+'\')"><li>' +
                        '<img src="'+data.noticePhoto+'" style="width: 100%;">' +
                        '</li></a>';
                }
            });
            doc.append(str);
            var pos = 0;
            var totalSlides = $('#slider-wrap ul#slider').find("li").length;
            var sliderWidth = $('#slider-wrap').width();
            // 判断轮播图片是否为空
            if (totalSlides == 0) {
                $('#slider-wrap').css({"display": "none"});
            } else {
                $('#slider-wrap').css({"display": "block"});
            }

            $('#slider-wrap ul li').width(sliderWidth)
            $(document).ready(function(){
                $('#slider-wrap ul#slider').width(sliderWidth*totalSlides);
                $('#next').click(function(){
                    slideRight();
                });
                //previous slide
                $('#previous').click(function(){
                    slideLeft();
                });
                //automatic slider
                var autoSlider = setInterval(slideRight, 3000);
                //for each slide
                $.each($('#slider-wrap ul li'), function() {
                    //create a pagination
                    var li = document.createElement('li');
                    $('#pagination-wrap ul').append(li);
                });
                countSlides();
                pagination();
                $('#slider-wrap').hover(
                    function(){ $(this).addClass('active'); clearInterval(autoSlider); },
                    function(){ $(this).removeClass('active'); autoSlider = setInterval(slideRight, 3000); }
                );

            });//DOCUMENT READY

            /***********
             SLIDE LEFT
             ************/
            function slideLeft(){
                pos--;
                if(pos==-1){ pos = totalSlides-1; }
                $('#slider-wrap ul#slider').css('left', -(sliderWidth*pos));

                //*> optional
                countSlides();
                pagination();
            }

            /************
             SLIDE RIGHT
             *************/
            function slideRight(){
                pos++;
                if(pos==totalSlides){ pos = 0; }
                $('#slider-wrap ul#slider').css('left', -(sliderWidth*pos));

                //*> optional
                countSlides();
                pagination();
            }

            /************************
             //*> OPTIONAL SETTINGS
             ************************/
            function countSlides(){
                $('#counter').html(pos+1 + ' / ' + totalSlides);
            }

            function pagination(){
                $('#pagination-wrap ul li').removeClass('active');
                $('#pagination-wrap ul li:eq('+pos+')').addClass('active');
            }
        }
    });
})