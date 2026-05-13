<?php
$pass = "andrei";
$hash = password_hash($pass, PASSWORD_BCRYPT);
echo "Generated hash: <br><br>";
echo "<code>" . $hash . "</code>";
?>