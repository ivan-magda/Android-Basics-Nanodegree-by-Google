/**
 * Copyright (c) 2016 Ivan Magda
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ivanmagda.inventory.model.object;

public class Product {

    /**
     * Product mId.
     */
    private int mId;

    /**
     * Product mName.
     */
    private String mName;

    /**
     * Product mPrice.
     */
    private double mPrice;

    /**
     * Product mQuantity.
     */
    private int mQuantity;

    /**
     * Product sold mQuantity.
     */
    private int mSoldQuantity;

    /**
     * Product mSupplier email address.
     */
    private String mSupplier;

    /**
     * Product mPicture.
     */
    private byte[] mPicture;

    /**
     * Receive quantity from the supplier.
     */
    private int mReceiveQuantity;

    public Product(int id, String name, double price, int quantity, int soldQuantity, String supplier, byte[] picture) {
        this.mId = id;
        this.mName = name;
        this.mPrice = price;
        this.mQuantity = quantity;
        this.mSoldQuantity = soldQuantity;
        this.mSupplier = supplier;
        this.mPicture = picture;
    }

    public Product(int id, String name, double price, int quantity, int soldQuantity, String supplier) {
        this.mId = id;
        this.mName = name;
        this.mPrice = price;
        this.mQuantity = quantity;
        this.mSoldQuantity = soldQuantity;
        this.mSupplier = supplier;
    }

    /**
     * Id.
     */

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    /**
     * Name.
     */

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Price.
     */

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }

    /**
     * Quantity.
     */

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        if (isValidQuantity(quantity))
            this.mQuantity = quantity;
    }

    /**
     * Sold quantity.
     */

    public int getSoldQuantity() {
        return mSoldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        if (isValidQuantity(soldQuantity))
            this.mSoldQuantity = soldQuantity;
    }

    /**
     * Receive quantity.
     */

    public int getReceiveQuantity() {
        return mReceiveQuantity;
    }

    public void setReceiveQuantity(int receiveQuantity) {
        if (isValidQuantity(receiveQuantity))
            this.mReceiveQuantity = receiveQuantity;
    }

    /**
     * Supplier.
     */

    public String getSupplier() {
        return mSupplier;
    }

    public void setSupplier(String supplier) {
        this.mSupplier = supplier;
    }

    /**
     * Picture.
     */

    public byte[] getPicture() {
        return mPicture;
    }

    public void setPicture(byte[] picture) {
        this.mPicture = picture;
    }

    /**
     * Edit quantities value helper methods.
     */

    public int incrementSaleQuantity() {
        if (mQuantity > 0) {
            setSoldQuantity(mSoldQuantity + 1);
            setQuantity(mQuantity - 1);
        }

        return mSoldQuantity;
    }

    public int decrementSaleQuantity() {
        if (mSoldQuantity > 0) {
            setSoldQuantity(mSoldQuantity - 1);
            setQuantity(mQuantity + 1);
        }
        return mSoldQuantity;
    }

    public int incrementReceiveQuantity() {
        int originalValue = mReceiveQuantity;
        setReceiveQuantity(originalValue + 1);

        if (originalValue != mReceiveQuantity)
            setQuantity(mQuantity + 1);

        return mReceiveQuantity;
    }

    public int decrementReceiveQuantity() {
        if (mQuantity > 0) {
            int originalValue = mReceiveQuantity;
            setReceiveQuantity(mReceiveQuantity - 1);

            if (originalValue != mReceiveQuantity)
                setQuantity(mQuantity - 1);
        }

        return mReceiveQuantity;
    }

    private boolean isValidQuantity(int quantity) {
        return quantity >= 0;
    }

}
