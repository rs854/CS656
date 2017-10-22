# CS656
Fall 2017 Networking Course

## Team Members
- Eugene Turgil
- Robert Smith
- Weng Li
- Meghna Agnish

## Installation Instructions

1. Download Android Studio: https://developer.android.com/studio/index.html
2. Open Android Studio
3. When prompted, click "Checkout from project from Version Control"
4. Click GitHub and enter your password.
5. Copy this URL: https://github.com/rs854/CS656.git (aka this repository)
6. Test it, adjust settings to your like and hit clone.
7. Congratulations, you are working in an android repo! 

## Roles & Responsibilities

To be defined...

## GIT Repository Rules

If you are creating a feature, fork off a development branch. Do all work there, once it is unit tested/accepted we can merge it into development branch.

## Credential Implementation (Temporary until we get proper authentication system)

Currently, I have implemented a temporary way to access credentials without hard coding them into the code. What you can do, until we get a better authentication management system is to set your environment variables to your credentials.
1. Click on the project right next to the Run button.
2. Select 'Edit Configurations'
3. Under VM options, type "-Dusername=username -Dpassword=password*" and replace the value password/username with your username and password respectively.

*I would go to the sign in security center on google and generate a temporary app password so you do not have to worry about changing the password.

## References

### SMTP

 - https://www.ietf.org/rfc/rfc2821.txt

### IMAP 
 - https://tools.ietf.org/html/rfc3501
