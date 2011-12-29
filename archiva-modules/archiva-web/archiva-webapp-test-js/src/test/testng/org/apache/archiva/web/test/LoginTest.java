package org.apache.archiva.web.test;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.archiva.web.test.parent.AbstractArchivaTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/*
 * Bug in TestNG. TESTNG-285: @Test(sequential=true) works incorrectly for classes with inheritance
 * http://code.google.com/p/testng/source/browse/trunk/CHANGES.txt
 * Waiting 5.9 release. It's comming soon.
 */

/**
 * Based on LoginTest of Emmanuel Venisse test.
 *
 * @author José Morales Martínez
 * @version $Id$
 */

@Test( groups = { "login" }, dependsOnGroups = { "about" } )
public class LoginTest
    extends AbstractArchivaTest
{
    @Test( alwaysRun = true )
    public void testWithBadUsername()
    {
        goToLoginPage();
        setFieldValue( "user-login-form-username", "badUsername" );
        clickLinkWithLocator( "modal-login-ok", true );
        assertTextPresent( "This field is required." );

    }

    @Test( dependsOnMethods = { "testWithBadUsername" }, alwaysRun = true )
    public void testWithBadPassword()
    {
        goToLoginPage();
        setFieldValue( "user-login-form-username", getProperty( "ADMIN_USERNAME" ) );
        setFieldValue( "user-login-form-password", "badPassword" );
        clickLinkWithLocator( "modal-login-ok", true );
        assertTextPresent( "You have entered an incorrect username and/or password" );
    }

    @Test( dependsOnMethods = { "testWithBadPassword" }, alwaysRun = true )
    public void testWithEmptyUsername()
    {
        goToLoginPage();
        setFieldValue( "user-login-form-password", "password" );
        clickLinkWithLocator( "modal-login-ok", true );
        //assertTextPresent( "User Name is required" );
        assertTextPresent( "This field is required." );
    }

    @Test( dependsOnMethods = { "testWithEmptyUsername" }, alwaysRun = true )
    public void testWithEmptyPassword()
    {
        goToLoginPage();
        setFieldValue( "user-login-form-username", getProperty( "ADMIN_USERNAME" ) );
        clickLinkWithLocator( "modal-login-ok", true );
        //assertTextPresent( "You have entered an incorrect username and/or password" );
        assertTextPresent( "This field is required." );
    }

    @Test( groups = { "loginSuccess" }, dependsOnMethods = { "testWithEmptyPassword" }, alwaysRun = true )
    public void testWithCorrectUsernamePassword()
    {
        goToLoginPage();
        setFieldValue( "user-login-form-username", getProperty( "ADMIN_USERNAME" ) );
        setFieldValue( "user-login-form-password", getProperty( "ADMIN_PASSWORD" ) );
        clickLinkWithLocator( "modal-login-ok", true );

        assertUserLoggedIn( getProperty( "ADMIN_USERNAME" ) );
    }

    @BeforeTest
    public void open()
        throws Exception
    {
        super.open();
    }

    @Override
    @AfterTest
    public void close()
        throws Exception
    {
        super.close();
    }
}