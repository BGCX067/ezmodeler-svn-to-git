<?php 

header("Content-type: text/xml" ); 

	$host     = "localhost:3306";	// mysql host server
	$database       = "i4change_mysql";		// database name
	$user = "i4change_mysql";	// mysql db -user
	$pass = "aamspw01";	// mysql db - password

$xml_output .="<abhi>";


$x=$_GET['x'];
$y=$_GET['y'];
$action=$_GET['action'];
$type=$_GET['type'];
$seq_id=$_GET['seq_id'];

$xml_output .= "<rectangle x=\"".$x."\" nm=\"".$y."\">";
$xml_output .="</rectangle>"; 

$linkID = mysql_connect($host, $user, $pass) or die("Could not connect to host." ); 
mysql_select_db($database, $linkID ) or die("Could not find database."); 

if ($action == "insert" && $type == "Activity" ){
            $sql="INSERT INTO window (x,y,width,height,text,seq_id) VALUES ('$x','$y','1','1','test','$seq_id')";
        } 
	  else if ($action == "update" && $type == "Activity") {
            $sql="UPDATE window SET x='$x', y='$y' WHERE seq_id='$seq_id'";
        } 
	  else if ($action == "truncate" && $type == "Activity") {
            $sql=" delete from window";
        } 
	  else if ($action == "delete" && $type == "Activity") {
            $sql="DELETE FROM window WHERE seq_id='$seq_id'";
	  }
	  else if ($action == "insert" && $type == "Connector") {
            $sql="DELETE FROM window WHERE seq_id='$seq_id'";
	  }
	  else if ($action == "insert" && $type == "Role" ) {
            $sql="INSERT INTO role (x,y,width,height,text,seq_id) VALUES ('$x','$y','800','150','test','$seq_id')";
        }
	  else if ($action == "update" && $type == "Role") {
            $sql="UPDATE role SET x='$x', y='$y' WHERE seq_id='$seq_id'";
        }
	  else if ($action == "delete" && $type == "Role") {
            $sql="DELETE FROM role WHERE seq_id='$seq_id'";
	  } 
		else {}








mysql_query($sql);

mysql_close();

$xml_output .="</abhi>";
echo $xml_output;


?> 
