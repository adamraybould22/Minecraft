package Voxel.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils
{
    /** Returns the contents of a file at the specified path. */
    public static String LoadAsString(String path)
    {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path)))
        {
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                result.append(line).append("\n");
            }
        }
        catch (IOException e)
        {
            System.err.println("Couldn't find the file at " + path);
        }

        return result.toString();
    }
}