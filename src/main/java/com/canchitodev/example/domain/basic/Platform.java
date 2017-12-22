/**
 * This content is released under the MIT License (MIT)
 *
 * Copyright (c) 2017, canchito-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 * @author 		José Carlos Mendoza Prego
 * @copyright	Copyright (c) 2017, canchito-dev (http://www.canchito-dev.com)
 * @license		http://opensource.org/licenses/MIT	MIT License
 * @link		https://github.com/canchito-dev/marshalling-xml-using-ox-mappers
 **/
package com.canchitodev.example.domain.basic;

public class Platform {
	
	private String name;
	private String developer;
	private String manufacturer;
	private String releaseDate;
	private String price;
	private String website;

	public Platform() {}

	public Platform(String name, String developer, String manufacturer, String releaseDate, String price,
			String website) {
		this.name = name;
		this.developer = developer;
		this.manufacturer = manufacturer;
		this.releaseDate = releaseDate;
		this.price = price;
		this.website = website;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return "Platform [name=" + name + ", developer=" + developer + ", manufacturer=" + manufacturer
				+ ", releaseDate=" + releaseDate + ", price=" + price + ", website=" + website + "]";
	}
}