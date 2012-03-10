<?php

    $ffmpeg = 'ffmpeg';

    // change "demo.mpg" to your mpg file name!
    $video  = dirname(__FILE__) . PATH_SEPARATOR . 'demo.flv';

    // change "demo.jpg" to whichever name you like or don't
    // for this example, the name of the output jpg file does not matter
    $image  = dirname(__FILE__) . PATH_SEPARATOR . 'demo.jpg';

    $second = 1;

    $cmd = "$ffmpeg -i $video 2>&1";
    if (preg_match('/Duration: ((\d+):(\d+):(\d+))/s', `$cmd`, $time)) {
        $total = ($time[2] * 3600) + ($time[3] * 60) + $time[4];
        $second = rand(1, ($total - 1));
    }

    $cmd = "$ffmpeg -i $video -deinterlace -an -ss $second -t 00:00:01 -r 1 -y -vcodec mjpeg -f mjpeg $image 2>&1";
    $return = `$cmd`;

    echo 'done!';

?>
