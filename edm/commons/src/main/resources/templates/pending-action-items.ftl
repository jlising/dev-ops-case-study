<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Pending Action Items</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<style>

     body, html, table,a{
		font-family: "Century Gothic", Arial, Helvetica, sans-serif;
		font-size: 11px !important;
		color: #666;
	}
	body{
		margin: 0;
		padding: 2px;
		background: #fff;
	}
	.logoRow{
		background-color: #34495E;
		padding: 10px;
	}
	.logo {
		width: 90px;
	}
	.messageBody{
		padding: 10px;
	}
	.footer{
		padding-left:10px;border-top: 1px solid #34495E;font-size:11px
	}
    </style>
</head>
<body>
    <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" style="border-collapse: collapse;">
        <tr>
            <td class="logoRow">
                <img src="${logoUrl}" alt="${logoUrl}" class="logo" />
            </td>
        </tr>
        <tr>
            <td class="messageBody">
                <p>Dear ${name},</p>
                <p>Below are your pending action items.</p>

				<table width="100%" cellspacing="2" cellpadding="5" style="background:#f7f4f4">
					 <tr style="background: #E7E7E7">
						<td>Action Item</td><td  width="20%">Due Date</td>
					 </tr>
					 <#list employeeActionItems as employeeActionItem>
						 <tr>
							<td>${employeeActionItem.actionItem.name}</td><td>${(employeeActionItem.dateDue?datetime?string('dd-MM-yyyy'))!"n/a"}</td>
						 </tr>
					 </#list>
				</table>
				<br/>
                <p>Kindly complete by logging into ${appLink}</p>
                <br/>
                <br/>
                <br/>
            </td>
        </tr>
        <tr>
            <td class="footer">
              Call Tree Management Tool 1.1.0 © 2018. All rights reserved.
            </td>
        </tr>
    </table>

</body>
</html>