/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.sql.parser.integrate.asserts.segment.definition;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.sql.parser.integrate.asserts.SQLCaseAssertContext;
import org.apache.shardingsphere.sql.parser.integrate.jaxb.domain.segment.impl.definition.ExpectedColumnPosition;
import org.apache.shardingsphere.sql.parser.sql.segment.ddl.column.position.ColumnAfterPositionSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.ddl.column.position.ColumnPositionSegment;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Column position assert.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ColumnPositionAssert {
    
    /**
     * Assert actual column position segment is correct with expected column position.
     * 
     * @param assertContext assert context
     * @param actual actual column position segment
     * @param expected expected column position
     */
    public static void assertIs(final SQLCaseAssertContext assertContext, final ColumnPositionSegment actual, final ExpectedColumnPosition expected) {
        String columnname = null;
        if (null != actual.getColumnName()) {
            columnname = actual.getColumnName().getQualifiedName();
        }
        String expectColumnName = null;
        if (null != expected.getColumn()) {
            expectColumnName = expected.getColumn().getName();
        }
        assertThat(assertContext.getText("Column change position name assertion error: "), columnname, is(expectColumnName));
        // TODO assert start index and stop index
        if (actual instanceof ColumnAfterPositionSegment) {
            assertNotNull(assertContext.getText("Assignments should exist."), expected.getColumn());
            assertThat(assertContext.getText("Column change position after name assertion error: "), 
                    ((ColumnAfterPositionSegment) actual).getColumnName().getIdentifier().getValue(), is(expected.getColumn().getName()));
            // TODO assert start index and stop index
        } else {
            assertNull(assertContext.getText("Assignments should not exist."), expected.getColumn());
        }
    }
}
