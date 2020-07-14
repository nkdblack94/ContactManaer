import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static final String path = "D:\\ContactsManager\\src\\main\\resources\\contact.csv";

    public static void main(String[] args) throws IOException {

        ContactManager contactManager = new ContactManager();
        int choose = -1;

        System.out.println("Contacts List");
        try {
            contactManager.readFile(path);
        } catch (Exception e) {
        }


        do {
            System.out.println("             ------------             ");
            System.out.println("-----CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ-----");
            System.out.println();
            System.out.println("Chọn chức năng theo số (để tiếp tục): ");
            System.out.println("          ----------        ");
            System.out.println("1. ------Xem danh sách------");
            System.out.println("2. --------Thêm mới---------");
            System.out.println("3. --------Cập nhập---------");
            System.out.println("4. ----------Xóa------------");
            System.out.println("5.-----Tìm kiếm theo tên----");
            System.out.println("6.Tìm kiếm theo số điện thoại");
            System.out.println("7. -------Đọc từ file-------");
            System.out.println("8. -------Ghi từ file-------");
            System.out.println("9. ----------Thoát----------");

            Scanner scanner = new Scanner(System.in);

            try {
                choose = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Đầu vào không hợp lệ phải là số");
            }

            switch (choose) {
                case 1:
                    showContactsList(contactManager);
                    break;
                case 2:
                    addNewContact(contactManager);
                    break;
                case 3:
                    try {
                        editContactsList(contactManager);
                    } catch (Exception e) {
                        System.out.println("Không có số điện thoại trong danh bạ");
                    }
                    break;
                case 4:
                    try {
                        deleteContacts(contactManager);
                    } catch (Exception e) {
                        System.out.println("Không có số điện thoại trong danh bạ");
                    }
                    break;
                case 5:
                    try{
                        searchFullName(contactManager);
                    }catch (Exception e){
                        System.out.println("Không có tên trong danh bạ");
                    }
                    break;
                case 6:
                    try {
                        searchPhoneNumber(contactManager);
                    } catch (Exception e) {
                        System.out.println("Không có số điện thoại trong danh bạ");
                    }
                    break;
                case 7:
                    contactManager.writeFile(path);
                    break;
                case 8:
                    contactManager.readFile(path);


            }
        } while (choose != 8);
    }

    public static void showContactsList(ContactManager contactManager) {
        System.out.println("Contacts list");
        contactManager.showContactsList();
    }

    public static void addNewContact(ContactManager contactManager) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String fullName;
        do {
            System.out.println("Họ tên");
            fullName = scanner.nextLine();
        } while (fullName.equals(""));
        String phoneNumber;
        do {
            System.out.println("Số điện thoại");
            phoneNumber = scanner.nextLine();
        } while (!phoneValidate(phoneNumber));
        System.out.println("Nhóm danh bạ");
        String group = scanner.nextLine();
        System.out.println("Giới tính");
        String gender = scanner.nextLine();
        System.out.println("Địa chỉ");
        String address = scanner.nextLine();
        System.out.println("Ngày sinh");
        String birth = scanner.nextLine();
        String mail;
        do {
            System.out.println("Email");
            mail = scanner.nextLine();
        } while (!emailValidate(mail));
        contactManager.addNewContact(fullName, phoneNumber, group, gender, address, birth, mail);
        contactManager.writeFile(path);
    }

    public static void editContactsList(ContactManager contactManager) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String phoneNumber;
        do {
            System.out.println("Số điện thoại");
            phoneNumber = scanner.nextLine();
        } while (!phoneValidate(phoneNumber) && !phoneNumber.equals(""));
        if (contactManager.isExistPhoneNumber(phoneNumber)) {
            System.out.println("Họ tên");
            String fullName = scanner.nextLine();
            System.out.println("Nhóm danh bạ");
            String group = scanner.nextLine();
            System.out.println("Giới tính");
            String gender = scanner.nextLine();
            System.out.println("Địa chỉ");
            String address = scanner.nextLine();
            System.out.println("Ngày sinh");
            String birth = scanner.nextLine();
            String mail;
            do {
                System.out.println("Email");
                mail = scanner.nextLine();
            } while (!emailValidate(mail));
            contactManager.update(fullName, phoneNumber, group, gender, address, birth, mail);
            System.out.println("Sửa thành công");
            contactManager.writeFile(path);
        }
    }

    public static void deleteContacts(ContactManager contactManager) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập số điện thoại cần xóa");
        String phoneNumber;
        boolean check;
        do {
            phoneNumber = scanner.nextLine();
            check = contactManager.isExistPhoneNumber(phoneNumber);
            if (check) {
                System.out.println("Nhập " + " Y " + " để xóa khổi bộ nhớ ");
                String input = scanner.nextLine();
                if (input.equals("Y")){
                    contactManager.deleteContact(phoneNumber);
                    System.out.println("Xóa thành công");
                }else {
                    return;
                }
                contactManager.writeFile(path);
            } else {
                System.out.println("Không tìm thấy số trong danh bạ");
            }
        } while (!check && !phoneNumber.equals(""));


    }

    public static void searchPhoneNumber(ContactManager contactManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập số điện thoại cần tìm");
        String phoneNumber = scanner.nextLine();
        contactManager.searchPhoneNumber(phoneNumber);
    }

    public static void searchFullName(ContactManager contactManager){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên cần tìm");
        String fullName = scanner.nextLine();
        contactManager.searchFullName(fullName);
    }

    public static boolean phoneValidate(String phone) {
        boolean checkPhone = Pattern.matches("([+](84)|0)+([0-9]{9})\\b", phone);
        return checkPhone;
    }

    public static boolean emailValidate(String mail) {
        boolean checkEmail = Pattern.matches("^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$", mail);
        return checkEmail;
    }
}
