package com.georgev22.menuapi.utilities;

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
 *     <li>{@code static @NotNull SerializableItemStack fromItemStack(ItemStack itemStack)} - Creates a new SerializableItemStack from an ItemStack.</li>
 *     <li>{@code SerializableItemStack(ItemStack itemStack)} - Constructs a new SerializableItemStack from an ItemStack.</li>
 *     <li>{@code ItemStack getItemStack()} - Retrieves the original ItemStack.</li>
 *     <li>{@code static @NotNull SerializableItemStack fromNBT(String nbtString)} - Creates a new SerializableItemStack from a string representation in NBT format.</li>
 *     <li>{@code static @NotNull List<String> serializeItemStacksToNBT(@NotNull List<ItemStack> itemStacks)} - Serializes a list of ItemStacks into a list of strings in NBT format.</li>
 *     <li>{@code static @NotNull List<ItemStack> deserializeItemStacksFromNBT(@NotNull List<String> nbtDataList)} - Deserializes a list of strings in NBT format into a list of ItemStacks.</li>
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
 *     oos.writeObject(SerializableItemStack.fromItemStack(player.getItemInHand()));
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

    /**
     * The transient ItemStack to be serialized.
     */
    private transient ItemStack itemStack;
    private transient ItemStack visualItemStack;

    private transient BigInteger amount;

    /**
     * Constructs a new SerializableItemStack from an ItemStack.
     *
     * @param itemStack The ItemStack to be serialized.
     */
    public SerializableItemStack(ItemStack itemStack, BigInteger amount) {
        this(itemStack, itemStack, amount);
    }

    /**
     * Constructs a new SerializableItemStack from an ItemStack.
     *
     * @param itemStack The ItemStack to be serialized.
     */
    public SerializableItemStack(ItemStack itemStack, ItemStack visualItemStack, BigInteger amount) {
        this.itemStack = itemStack;
        this.visualItemStack = visualItemStack;
        this.amount = amount;
    }

    /**
     * Creates a new SerializableItemStack from an ItemStack.
     *
     * @param itemStack The ItemStack to be wrapped.
     * @return A new SerializableItemStack instance.
     */
    public static @NotNull SerializableItemStack fromItemStack(ItemStack itemStack, BigInteger amount) {
        return new SerializableItemStack(itemStack, amount);
    }

    /**
     * Creates a new List of SerializableItemStack from an ItemStack List.
     *
     * @param itemStacks The ItemStacks list to be wrapped.
     * @return A new SerializableItemStack instance.
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
     * @param nbtString The string representation of the ItemStack in NBT format.
     * @return A new SerializableItemStack instance representing the deserialized ItemStack.
     * @throws SerializerException If there is an issue with the deserialization process.
     */
    public static @NotNull SerializableItemStack fromNBT(String nbtString) throws SerializerException {
        ReadWriteNBT readWriteNBT = NBT.parseNBT(nbtString);
        //noinspection DuplicatedCode
        BigInteger amount = new BigInteger(readWriteNBT.getOrDefault("sAmount", "1"));
        readWriteNBT.removeKey("sAmount");

        ItemStack visualItemStack = null;
        if (readWriteNBT.getKeys().contains("visualItemStack")) {
            ReadWriteNBT visualNBT = NBT.parseNBT(readWriteNBT.getOrDefault("visualItemStack", "{}"));
            visualItemStack = NBT.itemStackFromNBT(visualNBT);
            readWriteNBT.removeKey("visualItemStack");
        }

        ItemStack itemStack = NBT.itemStackFromNBT(readWriteNBT);
        if (itemStack == null) {
            throw new SerializerException("Could not deserialize item stack");
        }

        if (visualItemStack == null) {
            visualItemStack = itemStack.clone();
        }
        return new SerializableItemStack(itemStack, visualItemStack, amount);
    }

    /**
     * Serializes a list of ItemStacks into a list of strings in NBT format.
     *
     * @param itemStacks The list of ItemStacks to be serialized.
     * @return A list of strings in NBT format representing the serialized ItemStacks.
     */
    public static @NotNull List<String> serializeItemStacksToNBT(@NotNull Map<ItemStack, BigInteger> itemStacks) {
        List<String> nbtDataList = new ArrayList<>();
        for (Map.Entry<ItemStack, BigInteger> entry : itemStacks.entrySet()) {
            nbtDataList.add(SerializableItemStack.fromItemStack(entry.getKey(), entry.getValue()).toString());
        }
        return nbtDataList;
    }

    /**
     * Serializes a list of SerializableItemStack into a list of strings in NBT format.
     *
     * @param itemStacks The list of SerializableItemStack to be serialized.
     * @return A list of strings in NBT format representing the serialized ItemStacks.
     */
    public static @NotNull List<String> serializeItemStacksToNBT(@NotNull List<SerializableItemStack> itemStacks) {
        List<String> nbtDataList = new ArrayList<>();
        for (SerializableItemStack serializableItemStack : itemStacks) {
            nbtDataList.add(serializableItemStack.toString());
        }
        return nbtDataList;
    }

    /**
     * Deserializes a list of strings in NBT format into a list of SerializableItemStack.
     *
     * @param nbtDataList The list of strings in NBT format representing the serialized ItemStacks.
     * @return A list of SerializableItemStack representing the deserialized ItemStacks.
     * @throws SerializerException If there is an error during the deserialization process.
     */
    public static @NotNull List<SerializableItemStack> deserializeItemStacksFromNBT(@NotNull List<String> nbtDataList) throws SerializerException {
        List<SerializableItemStack> serializableItemStacks = new ArrayList<>();
        for (String nbt : nbtDataList) {
            serializableItemStacks.add(SerializableItemStack.fromNBT(nbt));
        }
        return serializableItemStacks;
    }

    /**
     * Retrieves the original ItemStack.
     *
     * @return The original ItemStack.
     */
    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    /**
     * Retrieves the visual ItemStack.
     *
     * @return The visual ItemStack.
     */
    public ItemStack getVisualItemStack() {
        return this.visualItemStack.clone();
    }

    /**
     * Retrieves the amount of the ItemStack.
     *
     * @return The amount of the ItemStack.
     */
    public BigInteger getAmount() {
        return amount;
    }

    /**
     * Sets the ItemStack.
     *
     * @param itemStack The ItemStack to be set.
     * @return The updated SerializableItemStack instance.
     */
    public SerializableItemStack setItemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack.clone();
        this.itemStack.setAmount(1);
        return this;
    }

    /**
     * Sets the visual ItemStack.
     *
     * @param visualItemStack The visual ItemStack to be set.
     * @return The updated SerializableItemStack instance.
     */
    public SerializableItemStack setVisualItemStack(@NotNull ItemStack visualItemStack) {
        this.visualItemStack = visualItemStack.clone();
        return this;
    }

    /**
     * Sets the amount of the ItemStack.
     *
     * @param amount The amount to be set.
     * @return The updated SerializableItemStack instance.
     */
    public SerializableItemStack setAmount(BigInteger amount) {
        this.amount = amount;
        return this;
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
            ReadWriteNBT readWriteNBT = NBT.parseNBT(inputStream.readUTF());
            //noinspection DuplicatedCode
            BigInteger amount = new BigInteger(readWriteNBT.getOrDefault("sAmount", "1"));
            readWriteNBT.removeKey("sAmount");

            ItemStack visualItemStack = null;
            if (readWriteNBT.getKeys().contains("visualItemStack")) {
                ReadWriteNBT visualNBT = NBT.parseNBT(readWriteNBT.getOrDefault("visualItemStack", "{}"));
                visualItemStack = NBT.itemStackFromNBT(visualNBT);
                readWriteNBT.removeKey("visualItemStack");
            }

            ItemStack itemStack = NBT.itemStackFromNBT(readWriteNBT);
            if (itemStack == null) {
                throw new SerializerException("Could not deserialize item stack");
            }

            if (visualItemStack == null) {
                visualItemStack = itemStack.clone();
            }

            this.itemStack = itemStack;
            this.visualItemStack = visualItemStack;
            this.amount = amount;
        } catch (Exception e) {
            throw new SerializerException("Error during deserialization of ItemStack: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        ReadWriteNBT nbt = NBT.itemStackToNBT(this.itemStack);
        ReadWriteNBT visualNBT = NBT.itemStackToNBT(this.visualItemStack);
        nbt.setString("sAmount", this.amount.toString());
        nbt.setString("visualItemStack", visualNBT.toString());
        return nbt.toString();
    }

    /**
     * Creates a Map representation of this class.
     * <p>
     * This class must provide a method to restore this class, as defined in
     * the {@link ConfigurationSerializable} interface javadocs.
     *
     * @return Map containing the current state of this class
     */
    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("serializedItemStack", this.toString());
        return data;
    }

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

    public static @Nullable SerializableItemStack valueOf(@NotNull String nbtData) {
        try {
            return SerializableItemStack.fromNBT(nbtData);
        } catch (Exception e) {
            return null;
        }
    }
}