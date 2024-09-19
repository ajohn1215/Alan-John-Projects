/**
* Author: Alan John
* Student ID: 170236456
* Recitation: 214.30 Sec 4
*/
import java.io.*;
import java.text.*;
import java.util.*;
/**
 * Represents an email with fields for recipient, CC, BCC, subject, body, and timestamp.
 */
class Email implements Serializable {
	private static final long serialVersionUID = 1L; 
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String body;
	private GregorianCalendar timestamp;
	/**
     * Returns the recipient of the email.
     *
     * @return the recipient of the email
     */
	public String getTo() {
		return to;
   	}
	/**
     * Sets the recipient of the email.
     *
     * @param to the new recipient of the email
     */
	public void setTo(String to) {
		this.to = to;
   	}

    /**
     * Returns the CC recipients of the email.
     *
     * @return the CC recipients of the email
     */
	public String getCc() {
		return cc;
   	}
	/**
     * Sets the CC recipients of the email.
     *
     * @param cc the new CC recipients of the email
     */
	public void setCc(String cc) {
		this.cc = cc;
   	}
	/**
     * Returns the BCC recipients of the email.
     *
     * @return the BCC recipients of the email
     */
	public String getBcc() {
		return bcc;
   	}
	/**
     * Sets the BCC recipients of the email.
     *
     * @param bcc the new BCC recipients of the email
     */
	public void setBcc(String bcc) {
		this.bcc = bcc;
   	}
	/**
     * Returns the subject of the email.
     *
     * @return the subject of the email
     */
	public String getSubject() {
		return subject;
   	}
	/**
     * Sets the subject of the email.
     *
     * @param subject the new subject of the email
     */
	public void setSubject(String subject) {
		this.subject = subject;
   	}
	/**
     * Returns the body of the email.
     *
     * @return the body of the email
     */
	public String getBody() {
		return body;
   	}
	/**
     * Sets the body of the email.
     *
     * @param body the new body of the email
     */
	public void setBody(String body) {
		this.body = body;
   	}
	/**
     * Returns the timestamp of the email.
     *
     * @return the timestamp of the email
     */
	public GregorianCalendar getTimestamp() {
		return timestamp;
   	}
	/**
     * Constructs an Email object with specified fields and initializes timestamp.
     *
     * @param to the recipient of the email
     * @param cc the CC recipients of the email
     * @param bcc the BCC recipients of the email
     * @param subject the subject of the email
     * @param body the body of the email
     */
	public Email(String to, String cc, String bcc, String subject, String body) {
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.body = body;
		this.timestamp = new GregorianCalendar();
   }
}
/**
 * Represents a folder that contains emails and supports sorting.
 */
class Folder implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Email> emails;
    private String name;
    private String currentSortingMethod;
    /**
     * Returns the list of emails in the folder.
     *
     * @return the list of emails
     */
    public ArrayList<Email> getEmails() {
        return emails;
    }
    /**
     * Returns the name of the folder.
     *
     * @return the name of the folder
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the folder.
     *
     * @param name the new name of the folder
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Adds an email to the folder and sorts the emails.
     *
     * @param email the email to be added
     */
    public void addEmail(Email email) {
        emails.add(email);
        sortEmails();
    }
    /**
     * Removes an email from the folder at the specified index.
     *
     * @param index the index of the email to be removed
     * @return the removed email
     */
    public Email removeEmail(int index) {
        return emails.remove(index);
    }
    /**
     * Sorts the emails in the folder by subject in ascending order.
     */
    public void sortBySubjectAscending() {
        currentSortingMethod = "subjectAsc";
        sortEmails();
    }
    /**
     * Sorts the emails in the folder by subject in descending order.
     */
    public void sortBySubjectDescending() {
        currentSortingMethod = "subjectDesc";
        sortEmails();
    }
    /**
     * Sorts the emails in the folder by date in ascending order.
     */
    public void sortByDateAscending() {
        currentSortingMethod = "dateAsc";
        sortEmails();
    }
    /**
     * Sorts the emails in the folder by date in descending order.
     */
    public void sortByDateDescending() {
        currentSortingMethod = "dateDesc";
        sortEmails();
    }
    /**
     * Sorts the emails in the folder based on the current sorting method.
     */
    private void sortEmails() {
        if (currentSortingMethod.equals("dateAsc")) {
            Collections.sort(emails, Comparator.comparing(Email::getTimestamp));
        } else if (currentSortingMethod.equals("dateDesc")) {
            Collections.sort(emails, Comparator.comparing(Email::getTimestamp).reversed());
        } else if (currentSortingMethod.equals("subjectAsc")) {
            Collections.sort(emails, Comparator.comparing(Email::getSubject));
        } else if (currentSortingMethod.equals("subjectDesc")) {
            Collections.sort(emails, Comparator.comparing(Email::getSubject).reversed());
        }
    }
    /**
     * Constructs a Folder with a specified name and initializes an empty email list.
     *
     * @param name the name of the folder
     */
    public Folder(String name) {
        this.emails = new ArrayList<>();
        this.name = name;
        this.currentSortingMethod = "dateDesc";
    }
}
/**
 * Custom exception indicating an issue with the mailbox.
 */
class MailboxException extends Exception {
	public MailboxException(String message) {
		super(message);
	}
}
/**
 * Custom exception indicating an issue with a folder.
 */
class FolderException extends Exception {
	public FolderException(String message) {
		super(message);
	}
}
/**
 * Custom exception indicating an issue with an email.
 */
class EmailException extends Exception {
	public EmailException(String message) {
		super(message);
	}
}
/**
 * Manages folders and emails and provides methods for interacting with the mailbox.
 */
public class Mailbox implements Serializable {
	private static final long serialVersionUID = 1L;
    private Folder inbox;
    private Folder trash;
    private ArrayList<Folder> folders;
    public static Mailbox mailbox;
    /**
     * Entry point of the application. Loads the mailbox and starts the menu.
     *
     * @param args command-line arguments
     * @throws MailboxException if an error occurs while loading the mailbox
     */
    public static void main(String[] args) throws MailboxException {
        mailbox = new Mailbox();
        mailbox.loadMailbox();
        mailbox.runMenu();
    }
    /**
     * Runs the main menu for interacting with the mailbox.
     */
    private void runMenu() {
        Scanner input = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Mailbox:");
            System.out.println("--------");
            for (Folder folder : folders) {
                System.out.println(folder.getName());
            }
            System.out.println();
            System.out.println("A – Add folder");
            System.out.println("R – Remove folder");
            System.out.println("C – Compose email");
            System.out.println("F – Open folder");
            System.out.println("I – Open Inbox");
            System.out.println("T – Open Trash");
            System.out.println("E – Empty Trash");
            System.out.println("Q – Quit");
            System.out.print("Enter a user option: ");
            String choice = input.nextLine().toUpperCase();
            try {
                switch (choice) {
                    case "A":
                        addFolder(input);
                        break;
                    case "R":
                        removeFolder(input);
                        break;
                    case "C":
                        composeEmail(input);
                        break;
                    case "F":
                        viewFolder(input);
                        break;
                    case "I":
                        viewInbox();
                        break;
                    case "T":
                        viewTrash();
                        break;
                    case "E":
                        emptyTrash();
                        break;
                    case "Q":
                        saveMailbox();
                        System.out.println("Program successfully exited and mailbox saved.");
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (MailboxException | FolderException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * Runs the menu for interacting with a specific folder.
     *
     * @param folder the folder to interact with
     * @param input the Scanner object for user input
     */
    private void runFolderMenu(Folder folder, Scanner input) {
        System.out.println(folder.getName());
        System.out.println();
        System.out.println("Index |        Time       | Subject");
        System.out.println("-----------------------------------");
        int index = 1;
        for (Email email : folder.getEmails()) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a MM/dd/yyyy");
            System.out.printf("%5d | %s | %s\n", index++, sdf.format(email.getTimestamp().getTime()), email.getSubject());
        }
        System.out.println();
        System.out.println("M – Move email");
        System.out.println("D – Delete email");
        System.out.println("V – View email contents");
        System.out.println("SA – Sort by subject line in ascending order");
        System.out.println("SD – Sort by subject line in descending order");
        System.out.println("DA – Sort by date in ascending order");
        System.out.println("DD – Sort by date in descending order");
        System.out.println("R – Return to main menu");
        System.out.print("Enter a user option: ");
        boolean quit1 = false;
        while (!quit1) {
            String choice = input.nextLine().toUpperCase();
            try {
                switch (choice) {
                    case "M":
                        moveEmail(folder, input);
                        break;
                    case "D":
                        deleteEmail(folder, input);
                        break;
                    case "V":
                        viewEmailContents(folder, input);
                        break;
                    case "SA":
                        folder.sortBySubjectAscending();
                        System.out.println("Sorted by subject in ascending order.");
                        break;
                    case "SD":
                        folder.sortBySubjectDescending();
                        System.out.println("Sorted by subject in descending order.");
                        break;
                    case "DA":
                        folder.sortByDateAscending();
                        System.out.println("Sorted by date in ascending order.");
                        break;
                    case "DD":
                        folder.sortByDateDescending();
                        System.out.println("Sorted by date in descending order.");
                        break;
                    case "R":
                        quit1 = true;
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (EmailException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * Constructs a new Mailbox with default folders.
     * Initializes the Inbox and Trash folders and adds them to the list of folders.
     */
    public Mailbox() {
        inbox = new Folder("Inbox");
        trash = new Folder("Trash");
        folders = new ArrayList<>();
        folders.add(inbox);
        folders.add(trash);
    }
    /**
     * Composes a new email and adds it to the Inbox folder.
     * Prompts the user to enter the recipient, carbon copy recipients, blind carbon copy recipients,
     * subject line, and body of the email.
     *
     * @param input the Scanner object for user input
     */
    private void composeEmail(Scanner input) {
        System.out.print("Enter recipient (To): ");
        String to = input.nextLine();
        System.out.print("Enter carbon copy recipients (CC): ");
        String cc = input.nextLine();
        System.out.print("Enter blind carbon copy recipients (BCC): ");
        String bcc = input.nextLine();
        System.out.print("Enter subject line: ");
        String subject = input.nextLine();
        System.out.print("Enter body: ");
        String body = input.nextLine();
        Email email = new Email(to, cc, bcc, subject, body);
        inbox.addEmail(email);
        System.out.println("Email successfully added to Inbox.");
    }
    /**
     * Displays the contents of the Inbox folder.
     * Launches the folder-specific menu for the Inbox.
     */
    private void viewInbox() {
        runFolderMenu(inbox, new Scanner(System.in));
    }
    /**
     * Displays the contents of the Trash folder.
     * Launches the folder-specific menu for the Trash.
     */
    private void viewTrash() {
        runFolderMenu(trash, new Scanner(System.in));
    }
    /**
     * Empties the Trash folder by removing all emails.
     * Clears all emails from the Trash folder and confirms the action.
     */
    private void emptyTrash() {
        trash.getEmails().clear();
        System.out.println("Trash folder emptied.");
    }

    /**
     * Loads the mailbox from a file, or initializes a new one if the file does not exist.
     * 
     * @throws MailboxException if an error occurs while loading the mailbox
     */
    private void loadMailbox() throws MailboxException {
        File file = new File("mailbox.obj");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                mailbox = (Mailbox) in.readObject();
                System.out.println("Mailbox successfully loaded.");
            } catch (IOException | ClassNotFoundException e) {
                throw new MailboxException("Error loading mailbox: " + e.getMessage());
            }
        } else {
            System.out.println("Previous save not found, starting with an empty mailbox.");
            mailbox = new Mailbox();
        }
    }
    /**
     * Saves the current state of the mailbox to a file.
     * 
     * @throws MailboxException if an error occurs while saving the mailbox
     */
    private void saveMailbox() throws MailboxException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("mailbox.obj"))) {
            out.writeObject(this); 
        } catch (IOException e) {
            throw new MailboxException("Error saving mailbox: " + e.getMessage());
        }
    }
    /**
     * Adds a new folder to the mailbox.
     * The folder name must be unique. Special folders "Inbox" and "Trash" cannot be added.
     *
     * @param input the Scanner object for user input
     * @throws FolderException if a folder with the given name already exists
     */
    private void addFolder(Scanner input) throws FolderException {
        System.out.print("Enter folder name: ");
        String name = input.nextLine();
        if (getFolder(name) == null) {
            Folder newFolder = new Folder(name);
            folders.add(newFolder);
            System.out.println("Folder added.");
        } else {
            throw new FolderException("Folder already exists.");
        }
    }
    /**
     * Removes a folder from the mailbox.
     * Special folders "Inbox" and "Trash" cannot be removed.
     *
     * @param input the Scanner object for user input
     * @throws FolderException if the folder does not exist or is a special folder
     */
    private void removeFolder(Scanner input) throws FolderException {
        System.out.print("Enter folder name: ");
        String name = input.nextLine();
        Folder folder = getFolder(name);
        if (folder != null && !folder.getName().equals("Inbox") && !folder.getName().equals("Trash")) {
            folders.remove(folder);
            System.out.println("Folder removed.");
        } else {
            throw new FolderException("Cannot remove special folders or non-existing folders.");
        }
    }
    /**
     * Views a specified folder by running the folder-specific menu.
     *
     * @param input the Scanner object for user input
     * @throws FolderException if the folder does not exist
     */
    private void viewFolder(Scanner input) throws FolderException {
        System.out.print("Enter folder name: ");
        String name = input.nextLine();
        Folder folder = getFolder(name);
        if (folder != null) {
            runFolderMenu(folder, input);
        } else {
            throw new FolderException("Folder not found.");
        }
    }
    /**
     * Moves an email from the current folder to another folder.
     * If the target folder does not exist, the email is moved to the Inbox.
     *
     * @param currentFolder the folder from which the email will be moved
     * @param input the Scanner object for user input
     * @throws EmailException if the email index is invalid
     */
    private void moveEmail(Folder currentFolder, Scanner input) throws EmailException {
        System.out.print("Enter the index of the email to move: ");
        int index = Integer.parseInt(input.nextLine()) - 1;
        if (index >= 0 && index < currentFolder.getEmails().size()) {
            Email email = currentFolder.removeEmail(index);
            System.out.println("Select a folder to move \"" + email.getSubject() + "\" to:");
            for (Folder folder : folders) {
                System.out.println(folder.getName());
            }
            String targetName = input.nextLine();
            Folder targetFolder = getFolder(targetName);
            if (targetFolder != null) {
                targetFolder.addEmail(email);
                System.out.println("\"" + email.getSubject() + "\" successfully moved to " + targetName + ".");
            } else {
                inbox.addEmail(email);
                System.out.println("Folder not found. Email moved to Inbox.");
            }
        } else {
            throw new EmailException("Invalid email index.");
        }
    }
    /**
     * Deletes an email from the current folder by moving it to the Trash.
     *
     * @param currentFolder the folder from which the email will be deleted
     * @param input the Scanner object for user input
     * @throws EmailException if the email index is invalid
     */
    private void deleteEmail(Folder currentFolder, Scanner input) throws EmailException {
        System.out.print("Enter email index: ");
        int index = Integer.parseInt(input.nextLine()) - 1;
        if (index >= 0 && index < currentFolder.getEmails().size()) {
            Email email = currentFolder.removeEmail(index);
            trash.addEmail(email);
            System.out.println("\"" + email.getSubject() + "\" has been moved to the trash.");
        } else {
            throw new EmailException("Invalid email index.");
        }
    }
    /**
     * Displays the contents of a specific email from the given folder.
     *
     * @param folder the folder containing the email
     * @param input the Scanner object for user input
     * @throws EmailException if the email index is invalid
     */
    private void viewEmailContents(Folder folder, Scanner input) throws EmailException {
        System.out.print("Enter email index: ");
        int index = Integer.parseInt(input.nextLine()) - 1;
        if (index >= 0 && index < folder.getEmails().size()) {
            Email email = folder.getEmails().get(index);
            System.out.println("From: " + email.getTo());
            System.out.println("CC: " + email.getCc());
            System.out.println("BCC: " + email.getBcc());
            System.out.println("Subject: " + email.getSubject());
            System.out.println("Body: " + email.getBody());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a MM/dd/yyyy");
            System.out.println("Sent: " + sdf.format(email.getTimestamp().getTime()));
        } else {
            throw new EmailException("Invalid email index.");
        }
    }
    /**
     * Retrieves a folder by its name from the list of folders.
     *
     * @param name the name of the folder to retrieve
     * @return the Folder object if found, otherwise null
     */
    private Folder getFolder(String name) {
        for (Folder folder : folders) {
            if (folder.getName().equals(name)) {
                return folder;
            }
        }
        return null;
    }
}