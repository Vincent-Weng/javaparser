/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2015 The JavaParser Team.
 *
 * This file is part of JavaParser.
 * 
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License 
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */
 
package com.github.javaparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

// FIXME this file does not seem to be generated by javacc. Is the doc wrong, or the javacc config?
/**
 * <p>
 * This class was generated automatically by javacc, do not edit.
 * </p>
 * <p>
 * Parse Java source code and creates Abstract Syntax Tree classes.
 * </p>
 *
 * @author Júlio Vilmar Gesser
 */
public final class JavaParser {
    private static final CommentsInserter commentsInserter = new CommentsInserter();
    
    private JavaParser() {
        // hide the constructor
    }

    public static boolean getDoNotConsiderAnnotationsAsNodeStartForCodeAttribution() {
        return commentsInserter.getDoNotConsiderAnnotationsAsNodeStartForCodeAttribution();
    }

    public static void setDoNotConsiderAnnotationsAsNodeStartForCodeAttribution(boolean newValue) {
        commentsInserter.setDoNotConsiderAnnotationsAsNodeStartForCodeAttribution(newValue);
    }

    public static boolean getDoNotAssignCommentsPreceedingEmptyLines() {
        return commentsInserter.getDoNotAssignCommentsPreceedingEmptyLines();
    }

    public static void setDoNotAssignCommentsPreceedingEmptyLines(boolean newValue) {
        commentsInserter.setDoNotAssignCommentsPreceedingEmptyLines(newValue);
    }

    public static CompilationUnit parse(final InputStream in,
                                        final String encoding) throws ParseException {
        return parse(in,encoding,true);
    }

    /**
     * Parses the Java code contained in the {@link InputStream} and returns a
     * {@link CompilationUnit} that represents it.
     *
     * @param in
     *            {@link InputStream} containing Java source code
     * @param encoding
     *            encoding of the source code
     * @return CompilationUnit representing the Java source code
     * @throws ParseException
     *             if the source code has parser errors
     */
    public static CompilationUnit parse(final InputStream in,
                                        String encoding,
                                        boolean considerComments) throws ParseException {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(in, encoding);
            try {
                return parse(inputStreamReader, considerComments);
            } finally {
                inputStreamReader.close();
            }
        } catch (IOException ioe) {
            throw new ParseException(ioe.getMessage());
        }
    }

    /**
     * Parses the Java code contained in the {@link InputStream} and returns a
     * {@link CompilationUnit} that represents it.
     *
     * @param in
     *            {@link InputStream} containing Java source code
     * @return CompilationUnit representing the Java source code
     * @throws ParseException
     *             if the source code has parser errors
     */
    public static CompilationUnit parse(final InputStream in)
            throws ParseException {
        return parse(in, Charset.defaultCharset().name(),true);
    }

    public static CompilationUnit parse(final File file, final String encoding)
            throws ParseException, IOException {
        return parse(file,encoding,true);
    }

    /**
     * Parses the Java code contained in a {@link File} and returns a
     * {@link CompilationUnit} that represents it.
     *
     * @param file
     *            {@link File} containing Java source code
     * @param encoding
     *            encoding of the source code
     * @return CompilationUnit representing the Java source code
     * @throws ParseException
     *             if the source code has parser errors
     */
    public static CompilationUnit parse(final File file, final String encoding, boolean considerComments)
            throws ParseException {
        try {
            final FileInputStream in = new FileInputStream(file);
            try {
                return parse(in, encoding, considerComments);
            } finally {
                in.close();
            }
        } catch (IOException ioe) {
            throw new ParseException(ioe.getMessage());
        }
    }

    /**
     * Parses the Java code contained in a {@link File} and returns a
     * {@link CompilationUnit} that represents it.
     *
     * @param file
     *            {@link File} containing Java source code
     * @return CompilationUnit representing the Java source code
     * @throws ParseException
     *             if the source code has parser errors
     * @throws IOException
     */
    public static CompilationUnit parse(final File file) throws ParseException,
            IOException {
        return parse(file, Charset.defaultCharset().name(),true);
    }

    public static CompilationUnit parse(final Reader reader)
            throws ParseException {
        return parse(reader, true);
    }
    
    public static CompilationUnit parse(final Reader reader, boolean considerComments)
            throws ParseException {
        try {
            String comments = SourcesHelper.readerToString(reader);
            CompilationUnit cu = new InstanceJavaParser(comments).parse();
            if (considerComments){
                commentsInserter.insertComments(cu, comments);
            }
            return cu;
        } catch (IOException ioe){
            throw new ParseException(ioe.getMessage());
        }
    }

    /**
     * Parses the Java block contained in a {@link String} and returns a
     * {@link BlockStmt} that represents it.
     *
     * @param blockStatement
     *            {@link String} containing Java block code
     * @return BlockStmt representing the Java block
     * @throws ParseException
     *             if the source code has parser errors
     */
    public static BlockStmt parseBlock(final String blockStatement)
            throws ParseException {
        return new InstanceJavaParser(blockStatement).parseBlock();
    }

    /**
     * Parses the Java statement contained in a {@link String} and returns a
     * {@link Statement} that represents it.
     *
     * @param statement
     *            {@link String} containing Java statement code
     * @return Statement representing the Java statement
     * @throws ParseException
     *             if the source code has parser errors
     */
    public static Statement parseStatement(final String statement) throws ParseException {
        return new InstanceJavaParser(statement).parseStatement();
    }

    /**
     * Parses the Java import contained in a {@link String} and returns a
     * {@link ImportDeclaration} that represents it.
     *
     * @param importDeclaration
     *            {@link String} containing Java import code
     * @return ImportDeclaration representing the Java import declaration
     * @throws ParseException
     *             if the source code has parser errors
     */
    public static ImportDeclaration parseImport(final String importDeclaration) throws ParseException {
        return new InstanceJavaParser(importDeclaration).parseImport();
    }

    /**
     * Parses the Java expression contained in a {@link String} and returns a
     * {@link Expression} that represents it.
     *
     * @param expression
     *            {@link String} containing Java expression
     * @return Expression representing the Java expression
     * @throws ParseException
     *             if the source code has parser errors
     */
    public static Expression parseExpression(final String expression) throws ParseException {
        return new InstanceJavaParser(expression).parseExpression();
    }

    /**
     * Parses the Java annotation contained in a {@link String} and returns a
     * {@link AnnotationExpr} that represents it.
     *
     * @param annotation
     *            {@link String} containing Java annotation
     * @return AnnotationExpr representing the Java annotation
     * @throws ParseException
     *             if the source code has parser errors
     */
    public static AnnotationExpr parseAnnotation(final String annotation) throws ParseException {
        return new InstanceJavaParser(annotation).parseAnnotation();
    }

    /**
     * Parses the Java body declaration(e.g fields or methods) contained in a
     * {@link String} and returns a {@link BodyDeclaration} that represents it.
     *
     * @param body
     *            {@link String} containing Java body declaration
     * @return BodyDeclaration representing the Java annotation
     * @throws ParseException
     *             if the source code has parser errors
     */
    public static BodyDeclaration<?> parseBodyDeclaration(final String body) throws ParseException {
        return new InstanceJavaParser(body).parseBodyDeclaration();
    }

}
