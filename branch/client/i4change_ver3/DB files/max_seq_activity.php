<?php 

header("Content-type: text/xml" ); 

	$host     = "localhost:3306";	// mysql host server
	$database       = "i4change_mysql";		// database name
	$user = "i4change_mysql";			// mysql db -user
	$pass = "aamspw01";			// mysql db - password




$linkID = mysql_connect($host, $user, $pass) or die("Could not connect to host." ); 
mysql_select_db($database, $linkID ) or die("Could not find database."); 

$query = "select if(max(seq_id) is null,1,max(seq_id)) seq_id from window"; 
$resultID = mysql_query($query, $linkID ) or die("Data not found."); 

$xml_output = "<?xml version=\"1.0\"?>\n" ; 
$xml_output .="<rectangle>";
for($x = 0 ; $x < mysql_num_rows($resultID) ; $x++){ 
    $row = mysql_fetch_assoc ($resultID); 


    $xml_output .= "<activity seq_id=\"" . $row['seq_id']."\" />"; 

} 

$xml_output .="</rectangle>";
echo $xml_output; 

?> 
