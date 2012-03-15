shuffle = function(o){ //v1.0
	for(var j, x, i = o.length; i; j = parseInt(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
	return o;
};

$(document).ready(function() {
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

        var command = '$("#thumb' + index +'").attr("src", "' + image.url + '")';
        setTimeout(command, timeouts[index] * 50);

        $('#photo-grid').append(img);
    });

    $('#photo-grid').isotope({
      itemSelector : '.thumbnail',
      layoutMode : 'masonry'
    });

});