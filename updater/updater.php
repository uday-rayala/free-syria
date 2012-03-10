<?php

//set_include_path(get_include_path() . PATH_SEPARATOR . ".");
require_once 'cloudfiles.php';

error_reporting(E_ALL);

$user = 'mustafa123';
$api_key = 'hidden';


$auth = new CF_Authentication($user, $api_key);
$auth->authenticate();
$conn = new CF_Connection($auth);

function get_info() {
	global $conn;

	return $conn->get_info();
}

function all_containers() {
	global $conn;

	return $conn->list_containers();
}

function all_video_uris() {
	global $conn;

	$container = $conn->get_container('syria-m4v');
	$objects = $container->get_objects();

	$to_uri = function($obj) {
		//echo "Content type: ", $obj->content_type;
		echo "name: ", $obj->name, "\n";
		//return $obj->public_streaming_uri();
		return $obj->public_uri();
	};

	$is_flv = function($obj) {
		if (preg_match("/.*\.m4v$/", $obj->name))
			return true;
		else
			return false;
	};

	$videos = array_filter($objects, $is_flv);
	$uris = array_map($to_uri, $videos);
	return $uris;
}

function replace_extension($filename, $new_extension) {
	return preg_replace('/\..+$/', $new_extension, $filename);
}

function generate_image($video_path) {
	$image_path = replace_extension($video_path, ".jpg");
	shell_exec("ffmpeg -i \"" . $video_path . "\" -ss 0 -vframes 1 -vcodec mjpeg -f image2 \"" . $image_path . "\"");
	shell_exec("mv \"" . $image_path . "\" images");
}

function generate_images() {
	foreach(glob('videos/{*.m4v,*.flv}', GLOB_BRACE) as $video)
	{
		echo "video: " . $video . "\n";
		generate_image($video);
	}
}

function download_videos() {
	$uris = all_video_uris();

	foreach ($uris as $uri) {
		echo "Downloading video: " . $uri;
		shell_exec("wget \"" . $uri . "\"");
	}

	shell_exec("mv *.m4v videos");
}



//echo "Info:\n";
//print_r(get_info());

//echo "Containers:\n";
//print_r(all_containers());

//echo "\nVideos:\n";
//print_r(all_video_uris());

download_videos();
generate_images();
