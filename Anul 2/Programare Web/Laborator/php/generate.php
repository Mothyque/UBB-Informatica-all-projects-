<?php
$pass = "andrei";
$hash = password_hash($pass, PASSWORD_BCRYPT);
echo "Copy this hash exactly:<br><br>";
echo "<code>" . $hash . "</code>";
?>