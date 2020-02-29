# 'charity': (Spring & JSP) - Docker

An application for managing charity donations, with UIs for users, institutions and admins. The communication is acheived through Spring Mail and Apache Kafka messaging. Everything is deployed on Docker.
<br>
<br>


## User flow:
#### 1 - Welcome page
![alt text](https://raw.githubusercontent.com/k-wasilewski/charity/master/screenshots/main-en.png)
This is the initial page that anyone can access.

#### 2 - Change language
![alt-text](https://raw.githubusercontent.com/k-wasilewski/charity/master/screenshots/main-pl.png)
The whole application is available in English and in Polish.

#### 3 - User-specific
![alt-text](https://raw.githubusercontent.com/k-wasilewski/charity/master/screenshots/user-specific.png)
After logging in you have access to your the donation form, your donations' list, and profile edit.

#### 4 - The donation form
![alt-text](https://raw.githubusercontent.com/k-wasilewski/charity/master/screenshots/donation-step1.png)
The donation form is a 4 step, SpringForm-validated process.

#### 5 - The donation summary
![alt-text](https://raw.githubusercontent.com/k-wasilewski/charity/master/screenshots/donation-summary.png)
Before submitting you can review your donation.

#### 5 - Forgotten password
![alt-text](https://raw.githubusercontent.com/k-wasilewski/charity/master/screenshots/forgotten-pwd1.png)
If you forget your password, you can submit a request for changing it to new one.

#### 6 - Spring Mail
![alt-text](https://github.com/k-wasilewski/charity/blob/master/screenshots/forgotten-pwd2.png)
After request, the link to changing user password, as well as activating the new registered user account, is sent via email.

#### 7 - Institution-specific
![alt-text](https://github.com/k-wasilewski/charity/blob/master/screenshots/instit-donations.png)
If you log in as an institution, you can view donations passed to your institution and mark selected ones as successfully passed.

#### 8 - Apache Kafka messaging
![alt-text](https://github.com/k-wasilewski/charity/blob/master/screenshots/kafka.png)
If a donation is made from user to institution, as well as if a donation is received at an institution, a message is sent from user to institution or from an institution to user respectively. The instant messaging is processed through Apache Kafka.

#### 9 - Admin-specific
![alt-text](https://raw.githubusercontent.com/k-wasilewski/charity/master/screenshots/admin.png)
If you log in as an administrator, you have access to users', institutions' and admins' list, as well as their details and deleting/blocking them.

