package com.georgev22.menuapi.utilities;

import com.georgev22.library.maps.HashObjectMap;
import com.georgev22.library.maps.ObjectMap;
import com.georgev22.menuapi.exceptions.SerializerException;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A serializable wrapper for Bukkit ItemStack, using NBT serialization.
 *
 * <p>The {@code SerializableItemStack} class allows for easy serialization and deserialization
 * of Bukkit {@link org.bukkit.inventory.ItemStack} objects using NBT (Named Binary Tag) serialization.
 * </p>
 *
 * <p>It implements the {@link Serializable} interface and includes custom serialization methods
 * to convert an ItemStack to a string representation during serialization and recreate the ItemStack
 * during deserialization.
 * </p>
 *
 * <p>The class includes the following methods:
 * <ul>
 *     <li>{@code static @NotNull SerializableItemStack fromItemStack(ItemStack itemStack, BigInteger amount)} - Creates a new SerializableItemStack from an ItemStack and amount.</li>
 *     <li>{@code static @NotNull List<SerializableItemStack> fromItemStacks(@NotNull Map<ItemStack, BigInteger> itemStacks)} - Creates a list of SerializableItemStack from a map of ItemStacks and amounts.</li>
 *     <li>{@code static @NotNull SerializableItemStack fromNBT(String nbtString)} - Creates a new SerializableItemStack from a string representation in NBT format.</li>
 *     <li>{@code static @NotNull List<String> serializeItemStacksToNBT(@NotNull Map<ItemStack, BigInteger> itemStacks)} - Serializes a map of ItemStacks and amounts into a list of strings in NBT format.</li>
 *     <li>{@code static @NotNull List<String> serializeItemStacksToNBT(@NotNull List<SerializableItemStack> itemStacks)} - Serializes a list of SerializableItemStack into a list of strings in NBT format.</li>
 *     <li>{@code static @NotNull List<SerializableItemStack> deserializeItemStacksFromNBT(@NotNull List<String> nbtDataList)} - Deserializes a list of strings in NBT format into a list of SerializableItemStack.</li>
 *     <li>{@code ItemStack getItemStack()} - Retrieves the original ItemStack.</li>
 *     <li>{@code ItemStack getVisualItemStack()} - Retrieves the visual ItemStack.</li>
 *     <li>{@code BigInteger getAmount()} - Retrieves the amount of the ItemStack.</li>
 *     <li>{@code SerializableItemStack setItemStack(@NotNull ItemStack itemStack)} - Sets the ItemStack.</li>
 *     <li>{@code SerializableItemStack setVisualItemStack(@NotNull ItemStack visualItemStack)} - Sets the visual ItemStack.</li>
 *     <li>{@code SerializableItemStack setAmount(BigInteger amount)} - Sets the amount of the ItemStack.</li>
 *     <li>{@code SerializableItemStack addData(@NotNull String key, String value)} - Adds data to the custom data map.</li>
 *     <li>{@code SerializableItemStack removeData(@NotNull String key)} - Removes data from the custom data map.</li>
 *     <li>{@code String getData(@NotNull String key)} - Retrieves data from the custom data map.</li>
 *     <li>{@code @NotNull Map<String, Object> serialize()} - Creates a Map representation of this class.</li>
 *     <li>{@code static @Nullable SerializableItemStack deserialize(@NotNull Map<String, Object> serialized)} - Creates a new SerializableItemStack from a Map representation.</li>
 *     <li>{@code static @Nullable SerializableItemStack valueOf(@NotNull String nbtData)} - Creates a new SerializableItemStack from a String representation.</li>
 * </ul>
 * </p>
 *
 * <p>Serialization and deserialization are handled through custom methods:
 * <ul>
 *     <li>{@code private void writeObject(@NotNull ObjectOutputStream outputStream)} - Serializes the ItemStack to a string using NBT serialization.</li>
 *     <li>{@code private void readObject(@NotNull ObjectInputStream inputStream)} - Deserializes the string representation to recreate the ItemStack.</li>
 * </ul>
 * </p>
 *
 * <p>It is important to note that the ItemStack is marked as transient to prevent
 * unnecessary serialization of potentially large or complex Bukkit objects.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("itemstack.ser"))) {
 *     oos.writeObject(SerializableItemStack.fromItemStack(player.getItemInHand(), BigInteger.ONE));
 * } catch (IOException e) {
 *     e.printStackTrace();
 * }
 * }</pre>
 * </p>
 *
 * <p>It is recommended to handle potential exceptions, such as {@link SerializerException},
 * during the serialization and deserialization process.
 * </p>
 */
public class SerializableItemStack implements Serializable, ConfigurationSerializable, com.georgev22.library.yaml.serialization.ConfigurationSerializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private transient ItemStack itemStack;
    private transient ItemStack visualItemStack;
    private transient ObjectMap<String, String> customData;
    private transient BigInteger amount;

    /**
     * Constructs a new SerializableItemStack from an ItemStack and amount.
     *
     * @param itemStack The ItemStack to be serialized.
     * @param amount    The amount of the ItemStack.
     */
    public SerializableItemStack(ItemStack itemStack, BigInteger amount) {
        this(itemStack, itemStack, amount);
    }

    /**
     * Constructs a new SerializableItemStack from an ItemStack, visual ItemStack, and amount.
     *
     * @param itemStack       The ItemStack to be serialized.
     * @param visualItemStack The visual ItemStack.
     * @param amount          The amount of the ItemStack.
     */
    public SerializableItemStack(ItemStack itemStack, ItemStack visualItemStack, BigInteger amount) {
        this.itemStack = itemStack;
        this.visualItemStack = visualItemStack;
        this.amount = amount;
        this.customData = new HashObjectMap<>();
    }

    /**
     * Creates a new SerializableItemStack from an ItemStack and amount.
     *
     * @param itemStack The ItemStack to be wrapped.
     * @param amount    The amount of the ItemStack.
     * @return A new SerializableItemStack instance.
     */
    public static @NotNull SerializableItemStack fromItemStack(ItemStack itemStack, BigInteger amount) {
        return new SerializableItemStack(itemStack, amount);
    }

    /**
     * Creates a new List of SerializableItemStack from an ItemStack Map.
     *
     * @param itemStacks The ItemStacks map to be wrapped.
     * @return A new List of SerializableItemStack instances.
     */
    public static @NotNull List<SerializableItemStack> fromItemStacks(@NotNull Map<ItemStack, BigInteger> itemStacks) {
        List<SerializableItemStack> serializableItemStacks = new ArrayList<>();
        for (Map.Entry<ItemStack, BigInteger> entry : itemStacks.entrySet()) {
            serializableItemStacks.add(fromItemStack(entry.getKey(), entry.getValue()));
        }
        return serializableItemStacks;
    }

    /**
     * Creates a new SerializableItemStack from a string representation in NBT format.
     *
     * <p>The {@code fromNBT} method parses a string representation in NBT (Named Binary Tag) format
     * and constructs a new {@link SerializableItemStack} from the parsed ItemStack data.
     * </p>
     *
     * <p>It utilizes the NBT library to parse the NBT string and convert it into a Bukkit {@link ItemStack}.
     * If the deserialization process fails or the resulting ItemStack is null, a {@link SerializerException}
     * is thrown to indicate the failure.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * String nbtString = "..." // NBT string representation of an ItemStack
     * try {
     *     SerializableItemStack serializableItemStack = SerializableItemStack.fromNBT(nbtString);
     *     // Use the serializableItemStack as needed
     * } catch (SerializerException e) {
     *     // Handle the exception, log the error, or take appropriate action
     *     e.printStackTrace();
     * }
     * }</pre>
     * </p>
     *
     * <p>It is recommended to handle the {@link SerializerException} and any other potential exceptions
     * during the deserialization process to ensure proper error reporting and user feedback.
     * </p>
     *
     * @param dataString The string representation of the ItemStack data in String format.
     * @return A new SerializableItemStack instance representing the deserialized ItemStack.
     * @throws SerializerException If there is an issue with the deserialization process.
     */
    public static @NotNull SerializableItemStack fromNBT(String dataString) throws SerializerException {
        ObjectMap<String, String> data = jsonToMap(dataString);

        //noinspection DuplicatedCode
        ItemStack itemStack = null;
        if (data.containsKey("itemStack")) {
            ReadWriteNBT readWriteNBT = NBT.parseNBT(data.get("itemStack"));
            itemStack = NBT.itemStackFromNBT(readWriteNBT);
        }
        if (itemStack == null) {
            throw new SerializerException("Could not deserialize item stack");
        }

        ItemStack visualItemStack = null;
        if (data.containsKey("visualItemStack")) {
            ReadWriteNBT readWriteNBT = NBT.parseNBT(data.get("visualItemStack"));
            visualItemStack = NBT.itemStackFromNBT(readWriteNBT);
        }

        if (visualItemStack == null) {
            visualItemStack = itemStack.clone();
        }

        BigInteger amount = new BigInteger(data.getOrDefault("amount", "1"));

        SerializableItemStack serializableItemStack = new SerializableItemStack(itemStack, visualItemStack, amount);

        jsonToMap(data.getOrDefault("customData", "{}")).forEach((serializableItemStack::addData));

        return serializableItemStack;
    }

    /**
     * Serializes a map of ItemStacks and amounts into a list of strings in NBT format.
     *
     * <p>The {@code serializeItemStacksToNBT} method provides a convenient way to serialize a map of ItemStacks
     * and their corresponding amounts into a list of string representations in NBT format.
     * </p>
     *
     * <p>It iterates through the map of ItemStacks and amounts, serializes each ItemStack into NBT format,
     * and collects the resulting string representations into a list.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * Map<ItemStack, BigInteger> itemStacks = new HashMap<>();
     * // Populate the itemStacks map with ItemStacks and amounts
     * List<String> nbtDataList = SerializableItemStack.serializeItemStacksToNBT(itemStacks);
     * // Use the nbtDataList as needed
     * }</pre>
     * </p>
     *
     * @param itemStacks The map of ItemStacks and amounts to be serialized.
     * @return A list of strings representing the serialized ItemStacks in NBT format.
     */
    public static @NotNull List<String> serializeItemStacksToNBT(@NotNull Map<ItemStack, BigInteger> itemStacks) {
        List<SerializableItemStack> serializableItemStacks = fromItemStacks(itemStacks);
        return serializeItemStacksToNBT(serializableItemStacks);
    }

    /**
     * Serializes a list of SerializableItemStack into a list of strings in NBT format.
     *
     * <p>The {@code serializeItemStacksToNBT} method provides a convenient way to serialize a list of
     * {@link SerializableItemStack} instances into a list of string representations in NBT format.
     * </p>
     *
     * <p>It iterates through the list of {@link SerializableItemStack} objects, serializes each one into NBT format,
     * and collects the resulting string representations into a list.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * List<SerializableItemStack> itemStacks = new ArrayList<>();
     * // Populate the itemStacks list with SerializableItemStack instances
     * List<String> nbtDataList = SerializableItemStack.serializeItemStacksToNBT(itemStacks);
     * // Use the nbtDataList as needed
     * }</pre>
     * </p>
     *
     * @param itemStacks The list of SerializableItemStack to be serialized.
     * @return A list of strings representing the serialized ItemStacks in NBT format.
     */
    public static @NotNull List<String> serializeItemStacksToNBT(@NotNull List<SerializableItemStack> itemStacks) {
        List<String> nbtDataList = new ArrayList<>();
        for (SerializableItemStack itemStack : itemStacks) {
            nbtDataList.add(itemStack.toString());
        }
        return nbtDataList;
    }

    /**
     * Deserializes a list of strings in NBT format into a list of SerializableItemStack.
     *
     * <p>The {@code deserializeItemStacksFromNBT} method provides a convenient way to deserialize a list of
     * string representations in NBT format back into a list of {@link SerializableItemStack} instances.
     * </p>
     *
     * <p>It iterates through the list of NBT strings, deserializes each one into a {@link SerializableItemStack},
     * and collects the resulting objects into a list.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * List<String> nbtDataList = new ArrayList<>();
     * // Populate the nbtDataList with NBT strings
     * try {
     *     List<SerializableItemStack> itemStacks = SerializableItemStack.deserializeItemStacksFromNBT(nbtDataList);
     *     // Use the itemStacks list as needed
     * } catch (SerializerException e) {
     *     // Handle the exception, log the error, or take appropriate action
     *     e.printStackTrace();
     * }
     * }</pre>
     * </p>
     *
     * <p>It is recommended to handle the {@link SerializerException} and any other potential exceptions
     * during the deserialization process to ensure proper error reporting and user feedback.
     * </p>
     *
     * @param nbtDataList The list of NBT strings representing the serialized ItemStacks.
     * @return A list of SerializableItemStack instances.
     * @throws SerializerException If there is an issue with the deserialization process.
     */
    public static @NotNull List<SerializableItemStack> deserializeItemStacksFromNBT(@NotNull List<String> nbtDataList) throws SerializerException {
        List<SerializableItemStack> itemStacks = new ArrayList<>();
        for (String nbtData : nbtDataList) {
            itemStacks.add(fromNBT(nbtData));
        }
        return itemStacks;
    }

    /**
     * Retrieves the original ItemStack.
     *
     * <p>The {@code getItemStack} method returns the original {@link ItemStack} that this
     * {@link SerializableItemStack} instance represents.
     * </p>
     *
     * <p>It can be used to access the underlying Bukkit ItemStack for further manipulation or inspection.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * SerializableItemStack serializableItemStack = ... // Obtain an instance of SerializableItemStack
     * ItemStack originalItemStack = serializableItemStack.getItemStack();
     * // Use the originalItemStack as needed
     * }</pre>
     * </p>
     *
     * @return The original ItemStack.
     */
    public ItemStack getItemStack() {
        return this.itemStack.clone();
    }

    /**
     * Retrieves the visual ItemStack.
     *
     * <p>The {@code getVisualItemStack} method returns the visual {@link ItemStack} that this
     * {@link SerializableItemStack} instance represents.
     * </p>
     *
     * <p>It can be used to access the visual representation of the ItemStack for display purposes.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * SerializableItemStack serializableItemStack = ... // Obtain an instance of SerializableItemStack
     * ItemStack visualItemStack = serializableItemStack.getVisualItemStack();
     * // Use the visualItemStack as needed
     * }</pre>
     * </p>
     *
     * @return The visual ItemStack.
     */
    public ItemStack getVisualItemStack() {
        return this.visualItemStack.clone();
    }

    /**
     * Retrieves the amount of the ItemStack.
     *
     * <p>The {@code getAmount} method returns the amount of the {@link ItemStack} that this
     * {@link SerializableItemStack} instance represents.
     * </p>
     *
     * <p>It can be used to access the quantity of the ItemStack.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * SerializableItemStack serializableItemStack = ... // Obtain an instance of SerializableItemStack
     * BigInteger amount = serializableItemStack.getAmount();
     * // Use the amount as needed
     * }</pre>
     * </p>
     *
     * @return The amount of the ItemStack.
     */
    public BigInteger getAmount() {
        return this.amount;
    }

    /**
     * Sets the ItemStack.
     *
     * <p>The {@code setItemStack} method updates the original {@link ItemStack} that this
     * {@link SerializableItemStack} instance represents.
     * </p>
     *
     * <p>It can be used to modify the underlying Bukkit ItemStack.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * SerializableItemStack serializableItemStack = ... // Obtain an instance of SerializableItemStack
     * ItemStack newItemStack = ... // Create or obtain a new ItemStack
     * serializableItemStack.setItemStack(newItemStack);
     * // The serializableItemStack now represents the new ItemStack
     * }</pre>
     * </p>
     *
     * @param itemStack The new ItemStack to set.
     * @return The updated SerializableItemStack instance.
     */
    public SerializableItemStack setItemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    /**
     * Sets the visual ItemStack.
     *
     * <p>The {@code setVisualItemStack} method updates the visual {@link ItemStack} that this
     * {@link SerializableItemStack} instance represents.
     * </p>
     *
     * <p>It can be used to modify the visual representation of the ItemStack.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * SerializableItemStack serializableItemStack = ... // Obtain an instance of SerializableItemStack
     * ItemStack newVisualItemStack = ... // Create or obtain a new visual ItemStack
     * serializableItemStack.setVisualItemStack(newVisualItemStack);
     * // The serializableItemStack now represents the new visual ItemStack
     * }</pre>
     * </p>
     *
     * @param visualItemStack The new visual ItemStack to set.
     * @return The updated SerializableItemStack instance.
     */
    public SerializableItemStack setVisualItemStack(@NotNull ItemStack visualItemStack) {
        this.visualItemStack = visualItemStack;
        return this;
    }

    /**
     * Sets the amount of the ItemStack.
     *
     * <p>The {@code setAmount} method updates the amount of the {@link ItemStack} that this
     * {@link SerializableItemStack} instance represents.
     * </p>
     *
     * <p>It can be used to modify the quantity of the ItemStack.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * SerializableItemStack serializableItemStack = ... // Obtain an instance of SerializableItemStack
     * BigInteger newAmount = ... // Set the desired amount
     * serializableItemStack.setAmount(newAmount);
     * // The serializableItemStack now represents the new amount
     * }</pre>
     * </p>
     *
     * @param amount The new amount to set.
     * @return The updated SerializableItemStack instance.
     */
    public SerializableItemStack setAmount(@NotNull BigInteger amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Adds data to the custom data map.
     *
     * @param key   The key of the data to be added.
     * @param value The value of the data to be added.
     * @return The updated SerializableItemStack instance.
     */
    public SerializableItemStack addData(@NotNull String key, String value) {
        this.customData.put(key, value);
        return this;
    }

    /**
     * Removes data from the custom data map.
     *
     * @param key The key of the data to be removed.
     * @return The updated SerializableItemStack instance.
     */
    public SerializableItemStack removeData(@NotNull String key) {
        this.customData.remove(key);
        return this;
    }

    /**
     * Retrieves data from the custom data map.
     *
     * @param key The key of the data to be retrieved.
     * @return The value of the data.
     */
    public String getData(@NotNull String key) {
        return this.customData.get(key);
    }

    /**
     * Custom serialization method using NBT serialization.
     *
     * @param outputStream The ObjectOutputStream to write the serialized data to.
     * @throws IOException         If an I/O error occurs during serialization.
     * @throws SerializerException If there is an issue with the serialization process.
     */
    @Serial
    private void writeObject(@NotNull ObjectOutputStream outputStream) throws IOException, SerializerException {
        try {
            outputStream.writeObject(this.toString());
        } catch (Exception e) {
            throw new SerializerException("Error during serialization of ItemStack: " + e.getMessage());
        }
    }

    /**
     * Custom deserialization method using NBT serialization.
     *
     * @param inputStream The ObjectInputStream to read the serialized data from.
     * @throws IOException            If an I/O error occurs during deserialization.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     * @throws SerializerException    If there is an issue with the deserialization process.
     */
    @Serial
    private void readObject(@NotNull ObjectInputStream inputStream) throws IOException, ClassNotFoundException, SerializerException {
        try {
            ObjectMap<String, String> data = jsonToMap(inputStream.readUTF());

            //noinspection DuplicatedCode
            ItemStack itemStack = null;
            if (data.containsKey("itemStack")) {
                ReadWriteNBT readWriteNBT = NBT.parseNBT(data.get("itemStack"));
                itemStack = NBT.itemStackFromNBT(readWriteNBT);
            }
            if (itemStack == null) {
                throw new SerializerException("Could not deserialize item stack");
            }

            ItemStack visualItemStack = null;
            if (data.containsKey("visualItemStack")) {
                ReadWriteNBT readWriteNBT = NBT.parseNBT(data.get("visualItemStack"));
                visualItemStack = NBT.itemStackFromNBT(readWriteNBT);
            }

            if (visualItemStack == null) {
                visualItemStack = itemStack.clone();
            }

            BigInteger amount = new BigInteger(data.getOrDefault("amount", "1"));

            this.itemStack = itemStack;
            this.visualItemStack = visualItemStack;
            this.amount = amount;
            this.customData = jsonToMap(data.getOrDefault("customData", "{}"));
        } catch (Exception e) {
            throw new SerializerException("Error during deserialization of ItemStack: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return mapToJson(
                new HashObjectMap<String, String>()
                        .append("itemStack", NBT.itemStackToNBT(this.itemStack).toString())
                        .append("visualItemStack", NBT.itemStackToNBT(this.visualItemStack).toString())
                        .append("amount", this.amount.toString())
                        .append("customData", mapToJson(this.customData))
        );
    }

    /**
     * Creates a Map representation of this class.
     *
     * @return Map containing the current state of this class
     */
    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("serializedItemStack", this.toString());
        return data;
    }

    /**
     * Creates a new SerializableItemStack from a Map representation.
     *
     * @param serialized Map containing the current state of this class
     * @return New SerializableItemStack
     */
    public static @Nullable SerializableItemStack deserialize(@NotNull Map<String, Object> serialized) {
        if (!serialized.containsKey("serializedItemStack")) {
            return null;
        }
        try {
            return SerializableItemStack.fromNBT((String) serialized.get("serializedItemStack"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new SerializableItemStack from a String representation.
     *
     * @param nbtData String containing the current state of this class
     * @return New SerializableItemStack
     */
    public static @Nullable SerializableItemStack valueOf(@NotNull String nbtData) {
        try {
            return SerializableItemStack.fromNBT(nbtData);
        } catch (Exception e) {
            return null;
        }
    }

    private static @NotNull String mapToJson(@NotNull Map<String, String> map) {
        StringBuilder jsonString = new StringBuilder("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jsonString.append("\"").append(entry.getKey()).append("\":");
            jsonString.append("\"").append(entry.getValue()).append("\"");
            jsonString.append(",");
        }

        if (jsonString.length() > 1) {
            jsonString.deleteCharAt(jsonString.length() - 1);
        }
        jsonString.append("}");
        return jsonString.toString();
    }

    private static @NotNull ObjectMap<String, String> jsonToMap(String jsonString) {
        ObjectMap<String, String> map = new HashObjectMap<>();
        jsonString = jsonString.substring(1, jsonString.length() - 1);
        String[] pairs = jsonString.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].replace("\"", "").trim();
            String value = keyValue[1].replace("\"", "").trim();
            map.put(key, value);
        }
        return map;
    }
}