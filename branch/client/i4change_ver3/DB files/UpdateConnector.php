<?php 

header("Content-type: text/xml" ); 

	$host     = "localhost:3306";	// mysql host server
	$database       = "i4change_mysql";		// database name
	$user = "i4change_mysql";	// mysql db -user
	$pass = "aamspw01";	// mysql db - password

$xml_output .="<abhi>";


$to_activity=$_GET['to_activity'];
$from_activity=$_GET['from_activity'];
$action=$_GET['action'];
$diagram_id=$_GET['diagram_id'];


$xml_output .= "<rectangle x=\"".$to_activity."\" nm=\"".$from_activity."\">";
$xml_output .="</rectangle>"; 

$linkID = mysql_connect($host, $user, $pass) or die("Could not connect to host." ); 
mysql_select_db($database, $linkID ) or die("Could not find database."); 

if ($action == "insert" ){
            $sql="INSERT INTO connectors (to_obj,from_obj,diagram_id) VALUES ('$to_activity','$from_activity','$diagram_id')";
        } 
	  else if ($action == "delete" && $type == "Activity") {
            $sql="DELETE FROM connectors WHERE to_obj='$to_activity' and from_obj='$from_activity'";
	}

		else {}



mysql_query($sql);

mysql_close();

$xml_output .="</abhi>";
echo $xml_output;


?> 
