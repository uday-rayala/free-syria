shuffle = function(o){
	for(var j, x, i = o.length; i; j = parseInt(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
	return o;
};

function setup_faces() {
    var jsonData = $.ajax({ type: "GET", url: '/images.json', async: false }).responseText;
    var images = JSON.parse(jsonData).images;
    var numberOfImages = images.length;
    var timeouts = [];

    for(i=0;i<numberOfImages;i++) {
        timeouts.push(i);
    }

    timeouts = shuffle(timeouts);

    $.each(images, function(index, image) {
        var img = $('<img/>');
        img.addClass('thumbnail');
        img.attr('id', 'thumb' + index);
        img.attr('width', image.dimensions[0]);
        img.attr('height', image.dimensions[1]);
		img.attr('video', image.video);

        var command = '$("#thumb' + index +'").attr("src", "' + image.url + '")';
        setTimeout(command, timeouts[index] * 50);

        $('#photo-grid').append(img);
    });

    $('#photo-grid').isotope({
      itemSelector : '.thumbnail',
      layoutMode : 'masonry'
    });
}

function setup_video_player(video_url) {
  var mediaArray = [];

  var jp = flowplayer("video-screen",
   {
  	src: "flowplayer-3.2.7.swf"
//  	src: "flowplayer.commercial-3.2.7.swf"
   },
   {
	   key : "#$5c6ff561a7d689aac88",
       logo : null,
       debug : false,
       log : { level  : 'warn' },
       clip :
       {
           url: video_url,
           autoPlay: true,
           provider: 'akamai',
           type : 'video',
       },

       scaling :
       {
            //scale:'half'
       },

       playlist: [],
       plugins:
       {
            controls: null,
            /*
               {
               url: 'flowplayer.controls-3.2.5.swf',
               },
               */
            akamai:
            {
                url: 'AkamaiFlowPlugin.swf'
            }
       },
      onFinish: function() { stop_video(); }
  });
}

function stop_video() {
    $f(0).stop();
    $('div#video-container').hide();
    $('div#video-screen').hide();
}

function setup_thumbnail_triggers() {
	$('img.thumbnail').on('click', function() {
		$('div#video-container').show();
        $('div#video-screen').show();

		var video = $(this).attr('video');
		setup_video_player(video);
	});
}

$(document).ready(function() {
    setup_faces();
    setup_thumbnail_triggers();
    $('#video-container').on('click', function() {
        stop_video();
    });
});
