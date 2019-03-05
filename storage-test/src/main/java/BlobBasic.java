import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.blob.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class BlobBasic {
    /*
     *  Play with storage SDK
     */
    public static CloudStorageAccount storageAccount;
    public static CloudBlobClient blobClient;
    public static CloudBlobContainer container;
    private static File srcFile;
    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;" +
                    "AccountName=simawest;" +
                    "AccountKey=C4jgATWmFy9+FRoetUywDwXrBAUzD2qu++Kkv6+raoGCSnAO2c4ZdG61OoLtzCUBTZ92kxzWxiRaWa/pjCuDMA==";

    //connect with storage account
    public static void runSetupAccountSamples() throws Exception {

        System.out.println("Connect with the Azure account app.");

        // Parse the connection string and create a blob client to interact with blob storage.

        storageAccount = CloudStorageAccount.parse(storageConnectionString);

    }


    //create blob client
    public static void runCreateBlobCLientSamples() throws Exception {

        System.out.println("Create a blob client.");

        blobClient = storageAccount.createCloudBlobClient();

    }

    //create container using blob client
    public static void runCreateContainerSamples() throws Exception {
        System.out.println("Create a container.");
        container = blobClient.getContainerReference("newhire");
        System.out.println("Creating container: " + container.getName());
        container.createIfNotExists(new BlobRequestOptions(), new OperationContext());
    }

    public static void main(String[] args) {
        try{

            System.out.println("Azure Storage Blob basic sample - Starting.");
            runSetupAccountSamples();
            runCreateBlobCLientSamples();
            runCreateContainerSamples();

            createTempFileForUpload();
            //Getting a blob reference

            CloudBlockBlob blob = container.getBlockBlobReference(srcFile.getName());
            //Creating blob and uploading file to it

            System.out.println("Uploading the sample file ");

            blob.uploadFromFile(srcFile.getAbsolutePath());



            //Listing contents of container

            for (ListBlobItem blobItem : container.listBlobs()) {

                System.out.println("URI of blob is: " + blobItem.getUri());
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Reason as follows" + e.getMessage());
        }
    }

    private static void createTempFileForUpload() throws Exception{
        //Creating a sample file

        srcFile = File.createTempFile("sampleFile", ".txt");

        System.out.println("Creating a sample file at: " + srcFile.toString());

        Writer output = new BufferedWriter(new FileWriter(srcFile));

        output.write("Hello Azure!");

        output.close();
    }
}
